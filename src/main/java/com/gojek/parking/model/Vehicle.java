package com.gojek.parking.model;

import java.util.Map;

public class Vehicle {
    private final String registrationNumber;
    private final String color;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public Vehicle( String registrationNumber, String color ) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

}
