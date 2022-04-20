package dev.langst.daotests;

import dev.langst.data.EmployeeDAO;
import dev.langst.data.EmployeeDAOPostgres;
import dev.langst.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeDaoTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgres();

    @Test
    void create_employee_test(){
        Employee zeus = new Employee("Thor", "Thunder");
        Employee savedEmployee = employeeDAO.createEmployee(zeus);
        Assertions.assertNotEquals(0, savedEmployee.getEmployeeId());
    }

    @Test
    void get_employee_by_id_test(){
        //This employee should already be in the database.
        //If not then change the hardcoded variables below
        int testId = 1;
        String testFirstName = "zeus";
        String testLastname = "beard";

        Employee testEmployee = employeeDAO.getEmployeeById(testId);

        Assertions.assertEquals(testId, testEmployee.getEmployeeId());
        Assertions.assertEquals(testFirstName, testEmployee.getFirstName());
        Assertions.assertEquals(testLastname, testEmployee.getLastName());
    }

    @Test
    void get_all_employees(){
        //These employees should always be in the database to test against
        //Change the hardcoded variables below if otherwise
        int testId1 = 1;
        int index1 = 0;
        int testId2 = 3;
        int index2 = 2;

        List<Employee> resultList = employeeDAO.getAllEmployees();

        Assertions.assertEquals(testId1, resultList.get(index1).getEmployeeId());
        Assertions.assertEquals(testId2, resultList.get(index2).getEmployeeId());
    }

    @Test
    void update_employee(){
        //This employee should already be in the database.
        //If not then change the hardcoded variables below
        int testId = 6;
        String testFirstName = "No Name";
        String testLastname = "Also No Name";
        Employee testEmployee = new Employee(testFirstName, testLastname);
        testEmployee.setEmployeeId(testId);

        Employee resultEmployee = employeeDAO.updateEmployee(testEmployee);

        Assertions.assertEquals(testFirstName, resultEmployee.getFirstName());
        Assertions.assertEquals(testLastname, resultEmployee.getLastName());

        //Revert changes for next test run
        testFirstName = "thor";
        testLastname = "thunder";
        testEmployee.setFirstName(testFirstName);
        testEmployee.setLastName(testLastname);
        employeeDAO.updateEmployee(testEmployee);

        Assertions.assertEquals(testFirstName, resultEmployee.getFirstName());
        Assertions.assertEquals(testLastname, resultEmployee.getLastName());
    }

    @Test
    void delete_employee(){
        //Must create an employee first to delete
        //May fail if the create employee method in EmployeeDAO fails
        Employee zeus = new Employee("Thor", "Thunder");
        Employee savedEmployee = employeeDAO.createEmployee(zeus);

        boolean result = employeeDAO.deleteEmployee(savedEmployee.getEmployeeId());
        Assertions.assertTrue(result);
    }
}
