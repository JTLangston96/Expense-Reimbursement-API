package dev.langst.services;

import dev.langst.entities.Expense;

import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    List<Expense> getAllExpenses();

    List<Expense> getExpensesByStatus(String status);

    Expense getExpenseById(int id);
}
