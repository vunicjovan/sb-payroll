package com.example.payroll.service;

import com.example.payroll.exception.EmployeeNotFoundException;
import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> list() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

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

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
