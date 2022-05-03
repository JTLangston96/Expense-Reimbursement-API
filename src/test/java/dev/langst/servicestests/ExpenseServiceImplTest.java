package dev.langst.servicestests;

import dev.langst.data.ExpenseDAO;
import dev.langst.data.ExpenseDAOPostgres;
import dev.langst.entities.Expense;
import dev.langst.services.ExpenseService;
import dev.langst.services.ExpenseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExpenseServiceImplTest {

    @Mock
    ExpenseDAO expenseDAOMock = Mockito.mock(ExpenseDAOPostgres.class);
    ExpenseService expenseService = new ExpenseServiceImpl(expenseDAOMock);

    @Test
    void createExpenseTest() {
        Expense testExpense = new Expense();
        testExpense.setExpenseId(1);
        testExpense.setAmount(200.21);

        when(expenseDAOMock.createExpense(testExpense)).thenReturn(testExpense);

        Expense savedExpense = expenseService.createExpense(testExpense);
        Assertions.assertNotEquals(0, savedExpense.getExpenseId());
    }

    @Test
    void getAllExpensesTest() {
        List<Expense> testExpenses = new ArrayList<>();
        testExpenses.add(new Expense());

        when(expenseDAOMock.getAllExpenses()).thenReturn(testExpenses);

        List<Expense> returnedExpenses = expenseService.getAllExpenses();
        Assertions.assertEquals(testExpenses.size(), returnedExpenses.size());
    }

    @Test
    void getExpensesByStatusTest() {
        final String ACCEPTED = "ACCEPTED";
        List<Expense> testExpenses = new ArrayList<>();
        testExpenses.add(new Expense());
        testExpenses.get(0).setStatus(ACCEPTED);

        when(expenseDAOMock.getAllExpenses()).thenReturn(testExpenses);

        List<Expense> returnedExpenses = expenseService.getExpensesByStatus(ACCEPTED);
        Assertions.assertEquals(ACCEPTED, returnedExpenses.get(0).getStatus());
    }

    @Test
    void getExpensesByEmployeeIdTest() {
        final int TEST_EMPLOYEE_ID = 1;
        List<Expense> testExpenses = new ArrayList<>();
        testExpenses.add(new Expense());
        testExpenses.get(0).setEmployeeId(TEST_EMPLOYEE_ID);

        when(expenseDAOMock.getAllExpenses()).thenReturn(testExpenses);

        List<Expense> returnedExpenses = expenseService.getExpensesByEmployeeId(TEST_EMPLOYEE_ID);
        Assertions.assertEquals(TEST_EMPLOYEE_ID, returnedExpenses.get(0).getEmployeeId());
    }

    @Test
    void getExpenseByIdTest() {
        final int TEST_EXPENSE_ID = 1;
        Expense testExpense = new Expense();
        testExpense.setExpenseId(TEST_EXPENSE_ID);

        when(expenseDAOMock.getExpenseById(TEST_EXPENSE_ID)).thenReturn(testExpense);

        Expense returnedExpense = expenseService.getExpenseById(TEST_EXPENSE_ID);
        Assertions.assertEquals(TEST_EXPENSE_ID, returnedExpense.getExpenseId());
    }

    @Test
    void updateExpenseTest() {
        Expense testExpense = new Expense(1, "PENDING", 32.45);
        testExpense.setExpenseId(0);

        when(expenseDAOMock.getExpenseById(testExpense.getExpenseId())).thenReturn(testExpense);
        when(expenseDAOMock.updateExpense(testExpense)).thenReturn(testExpense);

        Expense returnedExpense = expenseService.updateExpense(testExpense);
        Assertions.assertEquals(testExpense.getExpenseId(), returnedExpense.getExpenseId());
    }

    @Test
    void updateStatusTest() {
        Expense testExpense = new Expense(1, "PENDING", 32.45);
        Expense afterUpdate = new Expense(1, "APPROVE", 32.45);
        testExpense.setExpenseId(0);

        when(expenseDAOMock.getExpenseById(testExpense.getExpenseId())).thenReturn(testExpense);
        when(expenseDAOMock.updateExpense(any(Expense.class))).thenReturn(afterUpdate);

        Expense returnedExpense = expenseService.updateStatus(testExpense.getExpenseId(), afterUpdate.getStatus());
        Assertions.assertEquals(afterUpdate.getStatus(), returnedExpense.getStatus());
    }

    @Test
    void deleteExpenseTest() {
        final int TEST_EXPENSE_ID = 0;
        Expense testExpense = new Expense(0, "PENDING", 11.11);
        testExpense.setExpenseId(TEST_EXPENSE_ID);

        when(expenseDAOMock.getExpenseById(TEST_EXPENSE_ID)).thenReturn(testExpense);
        when(expenseDAOMock.deleteExpense(TEST_EXPENSE_ID)).thenReturn(true);

        Assertions.assertTrue(expenseService.deleteExpense(TEST_EXPENSE_ID));
    }
}