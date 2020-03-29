package com.gojek.parking.commands;

import com.gojek.parking.exception.DuplicateVehicleException;
import com.gojek.parking.model.Car;
import com.gojek.parking.model.ParkingLot;
import com.gojek.parking.model.Vehicle;

import javax.naming.SizeLimitExceededException;
import java.util.List;

public class CommandLineProcessor {

    ParkingLot parkingLot = new ParkingLot( 0 );

    public void parse( String input ) {
        String[] inputs = input.split( " " );
        String key = inputs[0];
        switch ( key ) {
            case Constants.CREATE_PARKING_LOT:
                int capacity = Integer.parseInt( inputs[1] );
                parkingLot = new ParkingLot( capacity );
                break;
            case Constants.PARK:
                try {
                    Integer slot = parkingLot.park( new Car( inputs[1], inputs[2] ) );
                    System.out.println( "Allocated Slot Number : " + slot );
                } catch ( DuplicateVehicleException | SizeLimitExceededException e ) {
                    System.out.println( e.getMessage() );
                } catch ( Exception exception ) {
                    System.out.println( exception.getMessage() );
                }
                break;
            case Constants.LEAVE:
                try {
                    int slotNumber = Integer.parseInt( inputs[1] );
                    if ( parkingLot.leave( slotNumber ) )
                        System.out.println( "Slot number " + slotNumber + " is free" );

                } catch ( Exception ex ) {
                    System.out.println( ex.getMessage() );
                }

                break;
            case Constants.STATUS:
                printStatus( parkingLot.getStatus());
                break;
            case Constants.REGISTRATION_NUMBERS_BY_COLOR:
                parkingLot.getRegistrationsByColor( inputs[1] );
                break;
            case Constants.SLOT_NUMBERS_BY_COLOR:
                parkingLot.getSlotsByColor( inputs[1] );
                break;
            case Constants.SLOT_NUMBER_BY_REGISTRATIONNUMBER:
                parkingLot.getSlotByRegistration( inputs[1] );
                break;
            default:
                break;
        }

    }

    private void printStatus( List<Vehicle> status ) {
        System.out.println( " Slot No.      Registration No        Colour" );
        status.forEach( System.out::println );
    }
}
