package com.nttdata.indhub.controller.impl;

public class Hola2 {
    private static final int id = 0;
    private static String brand;
    private static String model;

    public Hola2(){

    }

    public static String getBrand() {
        return brand;
    }

    public static void setBrand(String brand) {
        Hola2.brand = brand;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        Hola2.model = model;
    }
}
