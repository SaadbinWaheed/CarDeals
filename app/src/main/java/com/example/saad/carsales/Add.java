package com.example.saad.carsales;

/**
 * Created by Saad on 01/05/2017.
 */

public class Add {

    private String title,price, Car_owner, year, Add_id,key;
    private float Long,Lat;

    public Add() {
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

    public String getAdd_id() { return Add_id; }

    public void setAdd_id(String Add_id) { this.Add_id= Add_id; }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLat() {
        return Lat;
    }

    public void setLong(float longi){
        Long = longi;
    }

    public float getLong() {
        return Long;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey(){return this.key;}

    public void setPrice(String p){this.price = p;}

    public String getPrice(){return this.price;}
}
