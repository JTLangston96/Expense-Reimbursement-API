package dev.langst.services;

import dev.langst.data.EmployeeDAO;
import dev.langst.entities.Employee;
import dev.langst.exceptions.ObjectNotFound;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeDAO.createEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee foundEmployee = employeeDAO.getEmployeeById(id);
        if(foundEmployee == null){
            throw new ObjectNotFound();
        }
        else {
            return foundEmployee;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee foundEmployee = employeeDAO.updateEmployee(employee);
        if(foundEmployee == null){
            throw new ObjectNotFound();
        }
        else {
            return foundEmployee;
        }
    }

    @Override
    public boolean deleteEmployee(int id) {
        boolean foundEmployee = employeeDAO.deleteEmployee(id);
        if(foundEmployee == false){
            throw new ObjectNotFound();
        }
        else {
            return true;
        }
    }
}
