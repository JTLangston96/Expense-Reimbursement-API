package dev.langst.api;

import io.javalin.Javalin;

public class api {

    public static void main(String[] args) {
        Javalin api = Javalin.create();

            //        EMPLOYEES         //

//        POST /employees

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

        api.start();
    }
}
