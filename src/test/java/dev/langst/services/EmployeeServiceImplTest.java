package dev.langst.services;

import dev.langst.data.EmployeeDAO;
import dev.langst.data.EmployeeDAOPostgres;
import dev.langst.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeDAO employeeDAOMock = Mockito.mock(EmployeeDAOPostgres.class);
    EmployeeService employeeService = new EmployeeServiceImpl(employeeDAOMock);

    @Test
    void createEmployee() {
        Employee testEmployee = new Employee("Mars", "Jupiter");
        testEmployee.setEmployeeId(1);

        when(employeeDAOMock.createEmployee(testEmployee)).thenReturn(testEmployee);

        Employee returnedEmployee = employeeService.createEmployee(testEmployee);
        Assertions.assertNotEquals(0, returnedEmployee.getEmployeeId());
    }

    @Test
    void getEmployeeById() {
        Employee testEmployee = new Employee("Mars", "Jupiter");
        int testId = 5;
        testEmployee.setEmployeeId(testId);

        when(employeeDAOMock.getEmployeeById(testId)).thenReturn(testEmployee);

        Employee returnedEmployee = employeeService.getEmployeeById(testId);
        Assertions.assertEquals(testId, returnedEmployee.getEmployeeId());
    }

    @Test
    void getAllEmployees() {
        List<Employee> testEmployees = new ArrayList<>();
        testEmployees.add(new Employee("Mars", "Jupiter"));

        when(employeeDAOMock.getAllEmployees()).thenReturn(testEmployees);

        List<Employee> returnedEmployees = employeeService.getAllEmployees();
        Assertions.assertEquals(testEmployees.size(), returnedEmployees.size());
    }

    @Test
    void updateEmployee() {
        Employee testEmployee = new Employee("Mars", "Jupiter");

        when(employeeDAOMock.updateEmployee(testEmployee)).thenReturn(testEmployee);

        Employee updatedEmployee = employeeService.updateEmployee(testEmployee);
        Assertions.assertEquals(testEmployee.getEmployeeId(), updatedEmployee.getEmployeeId());
    }

    @Test
    void deleteEmployee() {
        int testId = 0;

        when(employeeDAOMock.deleteEmployee(testId)).thenReturn(true);

        Assertions.assertTrue(employeeService.deleteEmployee(testId));
    }
}