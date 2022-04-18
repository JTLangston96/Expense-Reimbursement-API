package dev.langst.entities;

public class Expense {

    private int expenseId;
    private int employeeId;
    private String status;
    private String issuer;
    private double amount;

    public Expense() {

    }

    public Expense(int expenseId, int employeeId, String status, String issuer, double amount) {
        this.expenseId = expenseId;
        this.employeeId = employeeId;
        this.status = status;
        this.issuer = issuer;
        this.amount = amount;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
