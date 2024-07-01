package com.udesc.healthier.model;

import com.google.gson.Gson;
import com.udesc.healthier.enums.TrainingLevel;

import java.util.Arrays;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private Double weight;
    private Double height;
    private TrainingLevel trainingLevel;

    public User() { }

    public User (String name, String email, String password, Double weight, Double height, TrainingLevel trainingLevel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.trainingLevel = trainingLevel;
    }

    public boolean isValidPassword(String password) {
        return password != null && password.equals(this.password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public TrainingLevel getTrainingLevel() {
        return trainingLevel;
    }

    public void setTrainingLevel(TrainingLevel trainingLevel) {
        this.trainingLevel = trainingLevel;
    }
}
