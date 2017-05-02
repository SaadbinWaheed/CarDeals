package com.example.saad.carsales;

/**
 * Created by Saad on 01/05/2017.
 */

public class Add {

    private String title, Car_owner, year;

    public Add() {
    }

    public Add(String title, String Car_owner, String year) {
        this.title = title;
        this.Car_owner = Car_owner;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCar_owner() {
        return Car_owner;
    }

    public void setCar_owner(String Car_owner) {
        this.Car_owner = Car_owner;
    }
}
