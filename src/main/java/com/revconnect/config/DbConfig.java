package com.revconnect.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConfig {

    private static final String URL =
            "jdbc:mysql://localhost:3306/revconnect";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "Bharath@2215";

    public static Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
        } catch (Exception e) {
            System.out.println("Database Connection Failed");
        }

        return connection;
    }

}
