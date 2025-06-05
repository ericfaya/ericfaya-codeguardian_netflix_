package com.nttdata.indhub.controller.impl;

public class Product {

    private static final int id = 0;
    private static String brand;
    private static String model;

    public Product(){

    }

    public static String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public static String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }

}
