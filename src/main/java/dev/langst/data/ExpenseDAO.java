package dev.langst.data;

import dev.langst.entities.Expense;

import java.util.List;

public interface ExpenseDAO {

    Expense createExpense(Expense expense);

    Expense getExpenseById(int id);

    List<Expense> getExpenseByEmployeeId(int id);

    Expense updateExpense(Expense expense);

    boolean deleteExpense(int id);

}
