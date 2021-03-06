package dev.langst.services;

import dev.langst.entities.Expense;

import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    List<Expense> getAllExpenses();

    List<Expense> getExpensesByStatus(String status);

    List<Expense> getExpensesByEmployeeId(int id);

    Expense getExpenseById(int id);

    Expense updateExpense(Expense expense);

    Expense updateStatus(int id, String status);

    boolean deleteExpense(int id);
}
