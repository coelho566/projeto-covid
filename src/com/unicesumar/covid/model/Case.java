package com.unicesumar.covid.model;

public class Case {

    private String id;

    private String inclusionDate;

    private String classification;

    private String age;

    private String sex;

    private String evolution;

    private String dateDeath;

    public Case() {
    }

    public Case(String id, String inclusionDate, String classification, String age, String sex, String evolution, String dateDeath) {
        this.id = id;
        this.inclusionDate = inclusionDate;
        this.classification = classification;
        this.age = age;
        this.sex = sex;
        this.evolution = evolution;
        this.dateDeath = dateDeath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInclusionDate() {
        return inclusionDate;
    }

    public void setInclusionDate(String inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getDateDeath() {
        return dateDeath;
    }

    public void setDateDeath(String dateDeath) {
        this.dateDeath = dateDeath;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id='" + id + '\'' +
                ", Data de inclusão ='" + inclusionDate + '\'' +
                ", Calssificação ='" + classification + '\'' +
                ", Idade ='" + age + '\'' +
                ", Sexo ='" + sex + '\'' +
                ", Evolução ='" + evolution + '\'' +
                ", Data de Óbito ='" + dateDeath + '\'' +
                '}';
    }
}
