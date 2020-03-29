package com.gojek.parking.model;

public class Car extends Vehicle {
    public Car( String registrationNumber, String color ) {
        super( registrationNumber, color );
    }

    public Car( String registrationNumber, String color, Integer slotNo ) {
        super( registrationNumber, color, slotNo );
    }
}
