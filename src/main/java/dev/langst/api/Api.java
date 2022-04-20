package dev.langst.api;

import com.google.gson.Gson;
import dev.langst.data.EmployeeDAOPostgres;
import dev.langst.data.ExpenseDAOPostgres;
import dev.langst.entities.Employee;
import dev.langst.entities.Expense;
import dev.langst.exceptions.NegativeExpense;
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
            String json = gson.toJson(employeeService.getEmployeeById(id));
            context.status(200);
            context.result(json);

        });

//        PUT /employees/150
        api.put("/employees/{id}", context -> {

            Gson gson = new Gson();
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);
            int id = Integer.parseInt(context.pathParam("id"));
            employee.setEmployeeId(id);
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            context.status(200);
            context.result("The following employee has been updated: \"" + updatedEmployee + "\"");

        });

//        DELETE /employees/190
        api.delete("employees/{id}", context -> {

            int id = Integer.parseInt(context.pathParam("id"));
            employeeService.deleteEmployee(id);
            context.status(200);
            context.result("Employee with the ID: \"" + id + "\' has been successfully deleted.");

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

//        GET /expenses/12

//        PUT /expenses/15

//        PATCH /expenses/20/approve

//        PATCH /expenses/20/deny

//        DELETE /expenses/19



            //        NESTED ROUTES        //

//        GET /employees/120/expenses

//        POST /employees/120/expenses

        api.start(7000);
    }
}
