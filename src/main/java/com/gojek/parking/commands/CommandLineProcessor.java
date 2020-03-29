package com.gojek.parking.commands;

import com.gojek.parking.delegate.DelegatePrint;
import com.gojek.parking.exception.DuplicateVehicleException;
import com.gojek.parking.model.Car;
import com.gojek.parking.model.ParkingLot;

import javax.naming.SizeLimitExceededException;

public class CommandLineProcessor {

    private final DelegatePrint delegatePrint = new DelegatePrint();
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
                    delegatePrint.allocateSlotFor( slot );
                } catch ( DuplicateVehicleException | SizeLimitExceededException e ) {
                    delegatePrint.exceptionMessage( e.getMessage() );

                } catch ( Exception exception ) {
                    delegatePrint.exceptionMessage( exception.getMessage() );
                }
                break;
            case Constants.LEAVE:
                try {
                    int slotNumber = Integer.parseInt( inputs[1] );
                    if ( parkingLot.leave( slotNumber ) )
                        delegatePrint.slotIsFree( slotNumber );

                } catch ( Exception ex ) {
                    delegatePrint.exceptionMessage( ex.getMessage() );
                }

                break;
            case Constants.STATUS:
                delegatePrint.printStatus( parkingLot.getStatus() );
                break;
            case Constants.REGISTRATION_NUMBERS_BY_COLOR:
                delegatePrint.printRegistrations( parkingLot.getRegistrationsByColor( inputs[1] ) );
                break;
            case Constants.SLOT_NUMBERS_BY_COLOR:
                delegatePrint.printSlots( parkingLot.getSlotsByColor( inputs[1] ) );
                break;
            case Constants.SLOT_NUMBER_BY_REGISTRATIONNUMBER:
                delegatePrint.printSlot( parkingLot.getSlotByRegistration( inputs[1] ) );
                break;
            default:
                break;
        }

    }

}
