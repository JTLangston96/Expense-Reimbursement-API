package dev.langst.data;

import dev.langst.entities.Employee;
import dev.langst.utilities.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO {

    private static final String EMPLOYEE_ID = "employee_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    private static final Logger logger = LogManager.getLogger(EmployeeDAOPostgres.class);

    @Override
    public Employee createEmployee(Employee employee) {

        String sql = "insert into employee values (default,?,?)";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int generatedId = rs.getInt(EMPLOYEE_ID);
            employee.setEmployeeId(generatedId);

            logger.info(String.format("An employee has been created in the database with the ID: %d", generatedId));

            return employee;

        } catch (SQLException e) {
            logger.error(String.format("There was an error with inserting employee \"%s %s\" into the database",
                    employee.getFirstName(), employee.getLastName()));

            return null;
        }
    }

    @Override
    public Employee getEmployeeById(int id) {

        String sql = "select * from employee where employee_id = ?";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, id);

            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();

            Employee employee = new Employee();

            int returnedId = rs.getInt(EMPLOYEE_ID);
            String firstName = rs.getString(FIRST_NAME);
            String lastname = rs.getString(LAST_NAME);

            employee.setEmployeeId(returnedId);
            employee.setFirstName(firstName);
            employee.setLastName(lastname);

            return employee;

        } catch (SQLException e) {
            logger.error(String.format("There was an error retrieving employee with the ID \"%d\" from the database.", id));
            return null;
        }

    }

    @Override
    public List<Employee> getAllEmployees() {

        String sql = "select * from employee";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.execute();
            ResultSet rs = ps.getResultSet();

            List<Employee> employeeList = new ArrayList<>();

            while(rs.next()) {
                Employee employee = new Employee();

                int returnedId = rs.getInt(EMPLOYEE_ID);
                String firstName = rs.getString(FIRST_NAME);
                String lastname = rs.getString(LAST_NAME);

                employee.setEmployeeId(returnedId);
                employee.setFirstName(firstName);
                employee.setLastName(lastname);

                employeeList.add(employee);
            }

            return employeeList;

        } catch (SQLException e) {
            logger.error("There was an error in retrieving all the employees from the database.");
            return new ArrayList<>();
        }

    }

    @Override
    public Employee updateEmployee(Employee employee) {

        String sql = "update employee set first_name = ?, last_name = ? where employee_id = ?";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getEmployeeId());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int returnedId = rs.getInt(EMPLOYEE_ID);
            String firstName = rs.getString(FIRST_NAME);
            String lastname = rs.getString(LAST_NAME);

            employee.setEmployeeId(returnedId);
            employee.setFirstName(firstName);
            employee.setLastName(lastname);

            logger.info(String.format("An employee has been updated in the database with the ID: %d", returnedId));

            return employee;

        } catch (SQLException e) {
            logger.error(String.format("There was an error updating the employee with ID \"%d\" from the database.",
                    employee.getEmployeeId()));
            return null;
        }

    }

    @Override
    public boolean deleteEmployee(int id) {

        String sql = "delete from employee where employee_id = ?";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, id);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            rs.getInt(EMPLOYEE_ID);

            logger.info(String.format("An employee has been deleted in the database with the ID: %d", id));

            return true;

        } catch (SQLException e) {
            logger.error(String.format("Employee with the ID \"%d\" was not found in the database and could not be deleted.", id));
            return false;
        }

    }
}
