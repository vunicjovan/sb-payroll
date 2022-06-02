package com.example.payroll.service.interfaces;

import com.example.payroll.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> list();

    Employee create(Employee employee);

    Employee get(Long id);

    Employee replace(Long id, Employee newEmployee);

    void delete(Long id);
}
