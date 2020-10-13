package com.unicesumar.covid.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public void createTableCovid(){

        String query = "CREATE TABLE IF NOT EXISTS tbl_cases" +
                "(id TEXT, " +
                "data_inclusao TEXT," +
                "classificacao TEXT," +
                "idade TEXT," +
                "sexo TEXT," +
                "evolucao TEXT," +
                "data_obito TEXT)";

        Connection conect = ConnectionFactory.getConnection();

        try{


            Statement stmt = conect.createStatement();
            stmt.execute(query);

            System.out.println("Table created successfully");

        }catch (SQLException ex){

            throw new RuntimeException(ex);

        }
    }
}
