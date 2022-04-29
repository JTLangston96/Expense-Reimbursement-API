package dev.langst.services;

import dev.langst.data.ExpenseDAO;
import dev.langst.entities.Expense;
import dev.langst.exceptions.InvalidStatusChange;
import dev.langst.exceptions.NegativeExpense;
import dev.langst.exceptions.ObjectNotFound;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public List<Expense> getExpensesByStatus(String status) {
       return expenseDAO.getAllExpenses()
               .stream()
               .filter(e -> e.getStatus().equals(status))
               .collect(Collectors.toList());
    }

    @Override
    public Expense getExpenseById(int id) {
        Expense expense = this.expenseDAO.getExpenseById(id);

        if(expense == null){
            throw new ObjectNotFound();
        }
        else{
            return expense;
        }
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense oldExpense = expenseDAO.getExpenseById(expense.getExpenseId());

        if(oldExpense == null){
            throw new ObjectNotFound();
        }
        else if(!oldExpense.getStatus().equals("PENDING")){
            throw new InvalidStatusChange();
        }
        else{
            oldExpense.setEmployeeId(expense.getEmployeeId());
            oldExpense.setAmount(expense.getAmount());
            return expenseDAO.updateExpense(oldExpense);
        }
    }

    @Override
    public Expense updateStatus(int id, String status) {
        Expense expense = this.expenseDAO.getExpenseById(id);
        if(expense == null){
            throw new ObjectNotFound();
        }
        else if(!expense.getStatus().equals("PENDING") ||
                (!status.equalsIgnoreCase("approve") && !status.equalsIgnoreCase("deny"))){
            throw new InvalidStatusChange();
        }
        else{
            expense.setStatus(status.toUpperCase());
            return this.expenseDAO.updateExpense(expense);
        }
    }
}
