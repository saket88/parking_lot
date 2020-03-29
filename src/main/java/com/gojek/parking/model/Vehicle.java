package com.gojek.parking.model;

public class Vehicle {
    private Integer slotNo;
    private final String registrationNumber;
    private final String color;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public Integer getSlotNo() {
        return slotNo;
    }

    public Vehicle( String registrationNumber, String color ) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public Vehicle( String registrationNumber, String color, Integer slotNo ) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.slotNo = slotNo;
    }

    @Override
    public String toString() {
        return "      " + slotNo +
                "          " + registrationNumber +
                "          " + color;
    }
}
