package dev.langst.data;

import dev.langst.entities.Employee;
import dev.langst.utilities.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO {


    @Override
    public Employee createEmployee(Employee employee) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into employee values (default,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int generatedId = rs.getInt("employee_id");
            employee.setEmployeeId(generatedId);

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee getEmployeeById(int id) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from employee where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();

            Employee employee = new Employee();

            int returnedId = rs.getInt("employee_id");
            String firstName = rs.getString("first_name");
            String lastname = rs.getString("last_name");

            employee.setEmployeeId(returnedId);
            employee.setFirstName(firstName);
            employee.setLastName(lastname);

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {

        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from employee";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.execute();
            ResultSet rs = ps.getResultSet();

            List<Employee> employeeList = new ArrayList();

            while(rs.next()) {
                Employee employee = new Employee();

                int returnedId = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastname = rs.getString("last_name");

                employee.setEmployeeId(returnedId);
                employee.setFirstName(firstName);
                employee.setLastName(lastname);

                employeeList.add(employee);
            }

            return employeeList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update employee set first_name = ?, last_name = ? where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getEmployeeId());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int returnedId = rs.getInt("employee_id");
            String firstName = rs.getString("first_name");
            String lastname = rs.getString("last_name");

            employee.setEmployeeId(returnedId);
            employee.setFirstName(firstName);
            employee.setLastName(lastname);

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteEmployee(int id) {

        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from employee where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            rs.getInt("employee_id");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
