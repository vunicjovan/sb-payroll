package com.example.payroll.service.implementations;

import com.example.payroll.exception.EmployeeNotFoundException;
import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.service.interfaces.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> list() {
        return repository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public Employee replace(Long id, Employee newEmployee) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());

                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);

                    return repository.save(newEmployee);
                });
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
