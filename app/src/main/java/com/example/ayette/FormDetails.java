package com.example.ayette;

import java.io.Serializable;

public class FormDetails implements Serializable {

    private String name;
    private String age;
    private String initialDate;
    private String finalDate;
    private String budget;

    public FormDetails(String name, String age, String initialDate, String finalDate, String budget) {
        this.name = name;
        this.age = age;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public String getBudget() {
        return budget;
    }
}
