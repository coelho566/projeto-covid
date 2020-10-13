package com.unicesumar.covid.config;

import java.sql.*;

public class ConnectionFactory {

    public static Connection getConnection() {
        String url = "jdbc:sqlite:dataBase/databaseCovid.db";

        try {
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
           throw new RuntimeException(ex);
        }
    }

}
