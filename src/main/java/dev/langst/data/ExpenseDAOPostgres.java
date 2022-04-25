package dev.langst.data;

import dev.langst.entities.Expense;
import dev.langst.utilities.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {

    private static final Logger logger = LogManager.getLogger(ExpenseDAOPostgres.class);


    @Override
    public Expense createExpense(Expense expense) {

        String sql = "insert into expense values (default, ?, ?, ?)";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, expense.getEmployeeId());
            ps.setString(2, expense.getStatus());
            ps.setDouble(3, expense.getAmount());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int generatedId = rs.getInt("expense_id");
            expense.setExpenseId(generatedId);

            return expense;

        } catch (SQLException e) {
            logger.error(String.format("There was an error inserting an Expense for employee \"%d\" into the database.",
                    expense.getEmployeeId()));
            return null;
        }

    }

    @Override
    public Expense getExpenseById(int id) {
        return null;
    }

    @Override
    public List<Expense> getExpenseByEmployeeId(int employeeId) {
        return new ArrayList<>();
    }

    @Override
    public List<Expense> getAllExpenses() {

        String sql = "select * from expense";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.execute();
            ResultSet rs = ps.getResultSet();

            List<Expense> expenses = new ArrayList<>();
            while(rs.next()){
                int expenseId = rs.getInt("expense_id");
                int employeeId = rs.getInt("employee_id");
                String status = rs.getString("status");
                double amount = rs.getDouble("amount");

                Expense expense = new Expense(employeeId, status, amount);
                expense.setExpenseId(expenseId);
                expenses.add(expense);
            }

            return expenses;

        } catch (SQLException e) {
            logger.error("Could not retrieve all Expenses from the database.");
            return new ArrayList<>();
        }

    }

    @Override
    public Expense updateExpense(Expense expense) {
        return null;
    }

    @Override
    public boolean deleteExpense(int id) {
        return false;
    }
}
