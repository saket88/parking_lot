package com.gojek.parking.delegate;

import com.gojek.parking.model.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class DelegatePrint {
    public DelegatePrint() {
    }

    public void printSlot( Integer slotByRegistration ) {
        System.out.println( slotByRegistration );
    }

    public void printSlots( List<Integer> slotsByColor ) {
        System.out.println( slotsByColor.stream()
                .map( String::valueOf )
                .collect( Collectors.joining( "," ) ) );
    }

    public void printRegistrations( List<Vehicle> registrationsByColor ) {
        System.out.println( registrationsByColor.stream().map( vehicle -> vehicle.getRegistrationNumber() )
                .collect( Collectors.joining( "," ) ) );
    }

    public void printStatus( List<Vehicle> status ) {
        System.out.println( " Slot No.      Registration No        Colour" );
        status.forEach( System.out::println );
    }

    public void allocateSlotFor( Integer slot ) {
        System.out.println( "Allocated Slot Number : " + slot );
    }

    public void exceptionMessage( String exception ) {
        System.out.println( exception );
    }

    public void slotIsFree( int slotNumber ) {
        System.out.println( "Slot number " + slotNumber + " is free" );
    }
}