package dev.langst.services;

import dev.langst.data.ExpenseDAO;
import dev.langst.entities.Expense;

public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense createExpense(Expense expense) {
        expense.setStatus("PENDING");
        return expenseDAO.createExpense(expense);
    }
}
