package dev.langst.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    public static Connection createConnection() {

        try {
            Connection conn = DriverManager.getConnection(System.getenv("EXPENSEDB"));
            return conn;
        }
        catch (SQLException e){
            logger.error("Connection to the Database could not be established.");
            return null;
        }
    }
}
