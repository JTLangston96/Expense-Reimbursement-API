package dev.langst.api;

import com.google.gson.Gson;
import dev.langst.data.EmployeeDAOPostgres;
import dev.langst.data.ExpenseDAOPostgres;
import dev.langst.entities.Employee;
import dev.langst.entities.Expense;
import dev.langst.exceptions.InvalidStatusChange;
import dev.langst.exceptions.NegativeExpense;
import dev.langst.exceptions.ObjectNotFound;
import dev.langst.services.EmployeeService;
import dev.langst.services.EmployeeServiceImpl;
import dev.langst.services.ExpenseService;
import dev.langst.services.ExpenseServiceImpl;
import io.javalin.Javalin;

public class Api {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgres());
    public static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOPostgres());

    public static void main(String[] args) {

        Javalin api = Javalin.create();

            //        EMPLOYEES         //

//        POST /employees
        api.post("/employees", context -> {

            String body = context.body();
            Gson gson = new Gson();
            Employee employee = gson.fromJson(body, Employee.class);
            Employee savedEmployee = employeeService.createEmployee(employee);
            context.status(201);
            context.result("Employee \"" + savedEmployee + "\" has been created.");

        });

//        GET /employees
        api.get("/employees", context -> {

            Gson gson = new Gson();
            String json = gson.toJson(employeeService.getAllEmployees());
            context.status(200);
            context.result(json);

        });

//        GET /employees/120
        api.get("/employees/{id}", context -> {

            Gson gson = new Gson();
            int id = Integer.parseInt(context.pathParam("id"));
            try {
                String json = gson.toJson(employeeService.getEmployeeById(id));
                context.status(200);
                context.result(json);
            }catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }

        });

//        PUT /employees/150
        api.put("/employees/{id}", context -> {

            Gson gson = new Gson();
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);
            int id = Integer.parseInt(context.pathParam("id"));
            employee.setEmployeeId(id);
            try {
                Employee updatedEmployee = employeeService.updateEmployee(employee);
                context.status(200);
                context.result("The following employee has been updated: \"" + updatedEmployee + "\"");
            }
            catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }

        });

//        DELETE /employees/190
        api.delete("employees/{id}", context -> {

            int id = Integer.parseInt(context.pathParam("id"));
            try {
                employeeService.deleteEmployee(id);
                context.status(200);
                context.result("Employee with the ID: \"" + id + "\" has been successfully deleted.");
            } catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }
        });


            //        EXPENSES          //

//        POST /expenses
        api.post("/expenses", context -> {
            try{
                String body = context.body();
                Gson gson = new Gson();
                Expense expense = gson.fromJson(body, Expense.class);
                Expense savedExpense = expenseService.createExpense(expense);
                context.status(201);
                context.result("The following expense \"" + savedExpense.toString() +
                        "\" has been created and is now pending approval");
            }catch (NegativeExpense e){
                context.status(400);
                context.result(e.getMessage());
            }

        });

//        GET /expenses
//        GET /expenses?status=pending
        api.get("/expenses", context -> {

            String statusCheck = context.queryParam("status");
            Gson gson = new Gson();
            String json;
            if(statusCheck == null) {
                json = gson.toJson(expenseService.getAllExpenses());
            }
            else{
                json = gson.toJson(expenseService.getExpensesByStatus(statusCheck.toUpperCase()));
            }
            context.status(200);
            context.result(json);

        });

//        GET /expenses/12
        api.get("/expenses/{id}", context -> {

            Gson gson = new Gson();
            int id = Integer.parseInt(context.pathParam("id"));
            try {
                String json = gson.toJson(expenseService.getExpenseById(id));
                context.status(200);
                context.result(json);
            }catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }
        });

//        PUT /expenses/15
        api.put("/expenses/{id}", context -> {

            Gson gson = new Gson();
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            int id = Integer.parseInt(context.pathParam("id"));
            expense.setExpenseId(id);
            try {
                Expense updatedExpense = expenseService.updateExpense(expense);
                context.status(200);
                context.result("The following employee has been updated: \"" + updatedExpense + "\"");
            }
            catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }
            catch (InvalidStatusChange e){
                context.status(400);
                context.result(e.getMessage());
            }

        });

//        PATCH /expenses/20/approve
//        PATCH /expenses/20/deny
        api.patch("/expenses/{id}/{status}", context -> {

            int id = Integer.parseInt(context.pathParam("id"));
            String status = context.pathParam("status");

            try {
                Expense updatedExpense = expenseService.updateStatus(id, status);
                context.status(200);
                context.result("The following expense has been updated: \"" + updatedExpense + "\"");
            }
            catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }
            catch (InvalidStatusChange e){
                context.status(400);
                context.result(e.getMessage());
            }
        });

//        DELETE /expenses/19
        api.delete("/expenses/{id}", context -> {

            try{
                int id = Integer.parseInt(context.pathParam("id"));
                expenseService.deleteExpense(id);
                context.status(201);
                context.result(String.format("The expense with ID %d has been deleted", id));
            }catch (ObjectNotFound e){
                context.status(404);
                context.result(e.getMessage());
            }catch (InvalidStatusChange e){
                context.status(400);
                context.result(e.getMessage());
            }
        });



            //        NESTED ROUTES        //

//        GET /employees/120/expenses
        api.get("/employees/{id}/expenses", context -> {

            Gson gson = new Gson();
            int id = Integer.parseInt(context.pathParam("id"));
            String json = gson.toJson(expenseService.getExpensesByEmployeeId(id));
            context.status(200);
            context.result(json);
        });
        
//        POST /employees/120/expenses
        api.post("/employees/{id}/expenses", context -> {
            try{
                int id = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Gson gson = new Gson();

                Expense expense = gson.fromJson(body, Expense.class);
                expense.setEmployeeId(id);
                Expense savedExpense = expenseService.createExpense(expense);

                context.status(201);
                context.result("The following expense \"" + savedExpense.toString() +
                        "\" has been created and is now pending approval");
            }catch (NegativeExpense e){
                context.status(400);
                context.result(e.getMessage());
            }

        });


        api.start(7000);
    }

}
