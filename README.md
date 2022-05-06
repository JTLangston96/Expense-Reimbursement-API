# Expense-Reimbursement-API

The Employee Reimbursement System (ERS) is REST API that helps manage the process of reimbursing employees for expenses. Employees can be created and edited via the API. Expenses for employees can be added and updated to pending and approved. Reviewed expenses can not be edited.

Employees and any expenses connected to the employees may be created using the API. There are some constraints to keep in mind. Every expense may only have a single employee as the issuer, and once an expense is approved or denied it may not be edited or deleted. Negative expenses are also not allowed to be created.

#Technologies Used
*Javalin
*PostgreSQL
*AWS Services
*JUnit
*Log4j
