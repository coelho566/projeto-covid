package com.unicesumar.covid.service;

import com.unicesumar.covid.config.ConnectionFactory;
import com.unicesumar.covid.model.Case;
import com.unicesumar.covid.model.DayDeath;
import com.unicesumar.covid.repository.CovidRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CovidService implements CovidRepository {

    private Connection connection;

    public Connection getConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        connection = ConnectionFactory.getConnection();

        return connection;
    }

    @Override
    public void insertCase(Case cases) {

        String sql = "INSERT INTO tbl_cases (id, data_inclusao, classificacao, idade, sexo , evolucao , data_obito) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);

            statement.setString(1, cases.getId());
            statement.setString(2, cases.getInclusionDate());
            statement.setString(3, cases.getClassification());
            statement.setString(4, cases.getAge());
            statement.setString(5, cases.getSex());
            statement.setString(6, cases.getEvolution());
            statement.setString(7, cases.getDateDeath());

            statement.executeUpdate();

            System.out.println("Registro inserido :" + cases.getId());

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
    }

    public void updateCaseTable() {

        String response = "http://dadosabertos.c3sl.ufpr.br/curitiba/CasosCovid19/2020-10-03_Casos_Covid_19_-_Base_de_Dados.csv";
        String line = null;
        BufferedReader in = null;
        List<String> cases = new ArrayList<>();

        try {
            URL stockURL = new URL(response);
            in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            while ((line = in.readLine()) != null) {
                cases.add(line);
            }
            saveCases(cases);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCases(List<String> lines) {

        lines.stream()
                .skip(1)
                .map(line -> line.split(";"))
                .map(cases -> {
                    Case aCase = new Case();
                    aCase.setId(cases[0]);
                    aCase.setInclusionDate(cases[1]);
                    aCase.setClassification(cases[2]);
                    aCase.setAge(cases[3]);
                    aCase.setSex(cases[4]);

                    if (cases.length > 5) {
                        aCase.setEvolution(cases[5]);
                    } else {
                        aCase.setEvolution("");
                    }

                    if (cases.length > 6) {
                        aCase.setDateDeath(cases[6]);
                    } else {
                        aCase.setDateDeath("");
                    }
                    return aCase;
                }).forEach(this::insertCase);
    }

    public List<Case> searchDataByAge(String ageData) {

        List<Case> cases = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cases ";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {

                String id = result.getString("id");
                String inclusionDate = result.getString("data_inclusao");
                String classification = result.getString("classificacao");
                String age = result.getString("idade");
                String sex = result.getString("sexo");
                String evolution = result.getString("evolucao");
                String dateDeath = result.getString("data_obito");

                cases.add(new Case(id, inclusionDate, classification, age,
                        sex, evolution, dateDeath));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return cases;
    }

    public List<DayDeath> getDayDeath() {

        List<DayDeath> cases = new ArrayList<>();
        String sql = "SELECT count(data_obito) AS cases, data_obito AS date FROM tbl_cases " +
                "WHERE data_obito <> '' GROUP BY data_obito ORDER BY count(data_obito) DESC LIMIT 10";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {

                Long num = result.getLong("cases");
                String date = result.getString("date");

                cases.add(new DayDeath(num, date));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return cases;
    }

    public Long getAverage(String sex) {

        String sql = queryAverage(sex);

        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            return result.getLong(1);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Long> getMode(String sex){

        String sql = queryMode(sex);

        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            List<Long> mods = new ArrayList<>();
            mods.add(result.getLong(1));
            mods.add(result.getLong(2));

            return mods;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String queryAverage(String sex) {

        String sql = "";

        if (sex.equalsIgnoreCase("M")) {
            sql = String.format("SELECT AVG(idade) FROM tbl_cases WHERE sexo = '%s' AND data_obito <> ''", sex);
        } else if (sex.equalsIgnoreCase("F")) {
            sql = String.format("SELECT AVG(idade) FROM tbl_cases WHERE sexo = '%s' AND data_obito <> ''", sex);
        } else {
            sql = "SELECT AVG(idade) from tbl_cases WHERE data_obito <> ''";
        }
        return sql;
    }

    private String queryMode(String sex) {
        String sql = "";

        if (sex.equalsIgnoreCase("M")) {
            sql = String.format("SELECT idade, count(idade) FROM tbl_cases WHERE sexo = '%s' " +
                    "AND data_obito <> '' GROUP BY idade ORDER BY count(idade) DESC LIMIT 1", sex);
        } else if (sex.equalsIgnoreCase("F")) {
            sql = String.format("SELECT idade, count(idade) FROM tbl_cases WHERE sexo = '%s' " +
                    "AND data_obito <> '' GROUP BY idade ORDER BY count(idade) DESC LIMIT 1", sex);
        } else {
            sql = "SELECT idade, count(idade) FROM tbl_cases WHERE data_obito <> ''" +
                    " GROUP BY idade ORDER BY count(idade) DESC LIMIT 1";
        }
        return sql;
    }
}
