# Expense-Reimbursement-API

The Employee Reimbursement System (ERS) is REST API that helps manage the process of reimbursing employees for expenses. Employees can be created and edited via the API. Expenses for employees can be added and updated to pending and approved. Reviewed expenses can not be edited.

## Technologies Used

* Java
* Maven
* Javalin
* PostgresSQL
* AWS Services
* JUnit
* Log4j

## Features

* Create a simple employee.
* Create an expense tied to a single employee.
* Edit and delete employees and expenses while following constraints.

To-do list:
* Refactor DAOs and Services to reduce repeated code.
* Create negative test cases for the service layer to increase code coverage.

## Getting Started
   
`git clone https://github.com/JTLangston96/Expense-Reimbursement-API.git`

The application relies on an environment variable named `EXPENSEDB` that should be a url route to your database. The only supported database language for this application is PostgreSQL.

The app runs on port 5000 so you can build the executable jar with `mvn package` while in the main directory and run or host the jar on whatever service you wish, like AWS.

## Usage

The application is a REST API with no frontend developed for it, so you will need a program like Postman to send http requests to and from endpoints. The endpoints are as follows:

### Employee Routes
- POST /employees 
  - returns a 201
- GET /employees
- GET /employees/120
  - returns a 404 if employee not found
- PUT /employees/150
  - returns a 404 if employee not found
- DELETE /employees/190
  - returns a 404 if employee not found


### Expenses Routes
- POST /expenses 
  - returns a 201
- GET /expenses
- GET /expenses?status=pending
  - also can get status approved or denied
- GET /expenses/12
  - returns a 404 if expense not found
- PUT /expenses/15
  - returns a 404 if expense not found
- PATCH /expenses/20/approve
  - returns a 404 if expense not found
- PATCH /expenses/20/deny
  - returns a 404 if expense not found
- DELETE /expenses/19
  - returns a 404 if car not found

### Nested Routes
- GET /employees/120/expenses
  - returns expenses for employee 120
- POST /employees/120/expenses
  - adds an expense to employee 120

