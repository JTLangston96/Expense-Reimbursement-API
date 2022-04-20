package dev.langst.services;

import dev.langst.data.ExpenseDAO;
import dev.langst.entities.Expense;
import dev.langst.exceptions.NegativeExpense;

public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense createExpense(Expense expense) {
        if(expense.getAmount() <= 0){
            throw new NegativeExpense();
        }
        expense.setStatus("PENDING");
        return expenseDAO.createExpense(expense);
    }
}
