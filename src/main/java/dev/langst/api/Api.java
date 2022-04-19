package dev.langst.api;

import com.google.gson.Gson;
import dev.langst.data.EmployeeDAOPostgres;
import dev.langst.entities.Employee;
import dev.langst.services.EmployeeService;
import dev.langst.services.EmployeeServiceImpl;
import io.javalin.Javalin;

public class Api {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgres());

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
            context.result("Employee \"" + savedEmployee.getFirstName() + " " +
                    savedEmployee.getLastName() + "\" has been created.");

        });

//        GET /employees

//        GET /employees/120

//        PUT /employees/150

//        DELETE /employees/190



            //        EXPENSES          //

//        POST /expenses

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
