package com.gojek.parking.exception;

public class DuplicateVehicleException extends Throwable {
    public DuplicateVehicleException( String message ) {
        super(message);
    }
}
