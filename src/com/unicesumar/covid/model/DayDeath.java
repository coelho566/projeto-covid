package com.unicesumar.covid.model;

public class DayDeath {

    private Long cases;

    private String date;

    public DayDeath() {
    }

    public DayDeath(Long cases, String date) {
        this.cases = cases;
        this.date = date;
    }

    public Long getCases() {
        return cases;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Data com mais casos { " +
                "Numero de casos: " + cases +
                ", data: '" + date + '\'' +
                '}';
    }
}
