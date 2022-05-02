package dev.langst.services;

import dev.langst.data.ExpenseDAO;
import dev.langst.entities.Expense;
import dev.langst.exceptions.InvalidStatusChange;
import dev.langst.exceptions.NegativeExpense;
import dev.langst.exceptions.ObjectNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseServiceImpl implements ExpenseService{

    private static final String PENDING = "PENDING";
    private static final String APPROVE = "APPROVE";
    private static final String DENY = "DENY";
    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    private static final Logger logger = LogManager.getLogger(ExpenseServiceImpl.class);

    @Override
    public Expense createExpense(Expense expense) {
        if(expense.getAmount() <= 0){
            throw new NegativeExpense();
        }
        expense.setStatus(PENDING);
        Expense returnedExpense = expenseDAO.createExpense(expense);
        if(returnedExpense == null){
            throw new ObjectNotFound();
        }
        else{
            return returnedExpense;
        }
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
    public List<Expense> getExpensesByEmployeeId(int id) {
        return expenseDAO.getAllExpenses()
                .stream()
                .filter(e -> e.getEmployeeId() == id)
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
        else if(!oldExpense.getStatus().equals(PENDING)){
            logger.debug(String.format("There was an attempt to change expense with ID \"%d\" without a \"%s\" status.",
                    oldExpense.getExpenseId(), PENDING));
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
        else if(!expense.getStatus().equals(PENDING) ||
                (!status.equalsIgnoreCase(APPROVE) && !status.equalsIgnoreCase(DENY))){
            logger.debug(String.format("There was an attempt to change expense with ID \"%d\" without a \"%s\" status.",
                    id, PENDING));
            throw new InvalidStatusChange();
        }
        else{
            expense.setStatus(status.toUpperCase());
            return this.expenseDAO.updateExpense(expense);
        }
    }

    @Override
    public boolean deleteExpense(int id) {
        Expense expense = this.expenseDAO.getExpenseById(id);
        if(expense == null){
            throw new ObjectNotFound();
        }
        else if(!expense.getStatus().equalsIgnoreCase(PENDING)){
            logger.debug(String.format("There was an attempt to change expense with ID \"%d\" without a \"%s\" status.",
                    id, PENDING));
            throw new InvalidStatusChange();
        }

        return this.expenseDAO.deleteExpense(id);
    }
}
