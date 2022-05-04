package dev.langst.daotests;

import dev.langst.data.ExpenseDAO;
import dev.langst.data.ExpenseDAOPostgres;
import dev.langst.entities.Employee;
import dev.langst.entities.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExpenseDaoTests {

    static ExpenseDAO expenseDAO = new ExpenseDAOPostgres();
    private static final String PENDING = "PENDING";

    @Test
    void create_expense_test(){
        //Creating an expense requires an employee already in the database
        //Change hardcoded employee ID if employee is deleted
        int employeeTestId = 1;
        String status = "PENDING";
        double amount = 67.99;

        Expense newExpense = new Expense(employeeTestId, status, amount);
        Expense savedExpense = expenseDAO.createExpense(newExpense);
        Assertions.assertNotEquals(0, savedExpense.getExpenseId());
    }

    @Test
    void get_all_expenses_test(){

        int testIndex = 4;
        int testExpenseId = 6;

        List<Expense> expenses = expenseDAO.getAllExpenses();

        Assertions.assertEquals(testExpenseId, expenses.get(testIndex).getExpenseId());
    }

    @Test
    void get_expense_by_id_test(){

        int testId = 4;

        Expense expense = expenseDAO.getExpenseById(testId);

        Assertions.assertEquals(testId, expense.getExpenseId());
    }

    @Test
    void update_expense_test(){
        //This expense should already be in the database with a pending status.
        //If not then change the hardcoded variables below
        int testId = 10;
        int testEmployeeId = 1;
        double testAmount = 52.21;

        Expense testExpense = new Expense();
        testExpense.setExpenseId(testId);
        testExpense.setEmployeeId(testEmployeeId);
        testExpense.setStatus(PENDING);
        testExpense.setAmount(testAmount);

        Expense resultExpense = expenseDAO.updateExpense(testExpense);

        Assertions.assertEquals(testEmployeeId, resultExpense.getEmployeeId());
        Assertions.assertEquals(testAmount, resultExpense.getAmount());

        //Revert changes for next test run
        testEmployeeId = 4;
        testAmount = 28.37;
        testExpense.setEmployeeId(testEmployeeId);
        testExpense.setAmount(testAmount);
        resultExpense = expenseDAO.updateExpense(testExpense);

        Assertions.assertEquals(testEmployeeId, resultExpense.getEmployeeId());
        Assertions.assertEquals(testAmount, resultExpense.getAmount());
    }

    @Test
    void delete_expense_test(){
        //Must create an employee first to delete
        //May fail if the create employee method in EmployeeDAO fails
        Expense testExpense = new Expense(1, PENDING, 23.54);
        Expense savedExpense = expenseDAO.createExpense(testExpense);

        boolean result = expenseDAO.deleteExpense(savedExpense.getExpenseId());
        Assertions.assertTrue(result);
    }
}
