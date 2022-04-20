package dev.langst.daotests;

import dev.langst.data.ExpenseDAO;
import dev.langst.data.ExpenseDAOPostgres;
import dev.langst.entities.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseDaoTests {

    static ExpenseDAO expenseDAO = new ExpenseDAOPostgres();

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

}
