package dev.langst.data;

import dev.langst.entities.Expense;
import dev.langst.utilities.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {

    private static final String EXPENSE_ID = "expense_id";
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String STATUS = "status";
    private static final String AMOUNT = "amount";

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

            int generatedId = rs.getInt(EXPENSE_ID);
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

        String sql = "select * from expense where expense_id = ?";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, id);

            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();

            Expense expense = new Expense();

            expense.setExpenseId(rs.getInt(EXPENSE_ID));
            expense.setEmployeeId(rs.getInt(EMPLOYEE_ID));
            expense.setStatus(rs.getString(STATUS));
            expense.setAmount(rs.getDouble(AMOUNT));

            return expense;

        } catch (SQLException e) {
            logger.error(String.format("There was an error retrieving an Expense with the Id %d.",
                    id));
            return null;
        }
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
                int expenseId = rs.getInt(EXPENSE_ID);
                int employeeId = rs.getInt(EMPLOYEE_ID);
                String status = rs.getString(STATUS);
                double amount = rs.getDouble(AMOUNT);

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

        String sql = "update expense set employee_id = ?, status = ?, amount = ? where expense_id = ?";

        try(Connection conn = ConnectionUtil.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, expense.getEmployeeId());
            ps.setString(2, expense.getStatus());
            ps.setDouble(3, expense.getAmount());
            ps.setInt(4, expense.getExpenseId());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            Expense updatedExpense = new Expense();

            expense.setExpenseId(rs.getInt(EXPENSE_ID));
            expense.setEmployeeId(rs.getInt(EMPLOYEE_ID));
            expense.setStatus(rs.getString(STATUS));
            expense.setAmount(rs.getDouble(AMOUNT));

            return expense;

        } catch (SQLException e) {
            logger.error(String.format("There was an error retrieving an Expense with the Id %d.",
                    expense.getExpenseId()));
            return null;
        }
    }

    @Override
    public boolean deleteExpense(int id) {
        return false;
    }
}
