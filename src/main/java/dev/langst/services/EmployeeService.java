package dev.langst.services;

import dev.langst.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    boolean deleteEmployee(int id);
}
