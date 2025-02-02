package com.gojek.parking.model;

import com.gojek.parking.exception.DuplicateVehicleException;

import javax.naming.SizeLimitExceededException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLot {
    private final int capacity;
    private ConcurrentHashMap<Vehicle, Integer> parkingMap;
    private TreeSet<Integer> availableSlots;
    private static final int STARTING_SLOT = 1;

    public int getCapacity() {
        return capacity;
    }

    private void setParkingMap() {
        parkingMap = new ConcurrentHashMap<>( capacity );
    }

    public ParkingLot( int capacity ) {
        this.capacity = capacity;
        setParkingMap();
        availableSlots= new TreeSet<>( );
    }

    public Integer park( Vehicle vehicle ) throws DuplicateVehicleException, SizeLimitExceededException {
        validate( vehicle );
        if ( isStartingMap( vehicle ) ) {
            return STARTING_SLOT;
        }
        return slotOf( vehicle );
    }

    private Integer slotOf( Vehicle vehicle ) {
        int value = determineImmediateSlot();
        constructMap( vehicle, value );
        return value;
    }

    private void constructMap( Vehicle vehicle, int value ) {
        parkingMap.put( vehicle, value );
    }

    private int determineImmediateSlot() {
        if ( !availableSlots.isEmpty() ){
            Integer first = availableSlots.first();
            availableSlots.remove( first );
            return first;
        }
        return Collections.max( parkingMap.values() ) + 1;
    }

    private boolean isStartingMap( Vehicle vehicle ) {
        if ( parkingMap.size() == 0 ) {
            parkingMap.putIfAbsent( vehicle, 1 );
            return true;
        }
        return false;
    }

    private void validate( Vehicle vehicle ) throws DuplicateVehicleException, SizeLimitExceededException {
        if ( getSlot( vehicle ) != null ) {
            throw new DuplicateVehicleException( "This is already present" );
        }
        if ( parkingMap.size() == capacity ) {
            throw new SizeLimitExceededException( "Sorry, parking lot is already full" );
        }
    }

    private Integer getSlot( Vehicle vehicle ) {
        return parkingMap.get( vehicle );
    }

    public ConcurrentHashMap<Vehicle, Integer> getParkingMap() {
        return parkingMap;
    }

    public boolean leave( Integer vehicleSlot ) {
        boolean isPresent = parkingMap.values().remove( vehicleSlot );
        if ( !isPresent )
            throw new NoSuchElementException( " The slot " + vehicleSlot + " is not available" );
        availableSlots.add( vehicleSlot );
        return isPresent;
    }

    public List<Integer> getSlotsByColor( String color ) {
        return parkingMap.entrySet().stream()
                .filter( vehicleIntegerEntry -> vehicleIntegerEntry.getKey().getColor().equals( color ) )
                .map( vehicleIntegerEntry -> vehicleIntegerEntry.getValue() )
                .collect( Collectors.toList() );
    }

    public Integer getSlotByRegistration( String registrationNumber ) {
        return parkingMap.entrySet().stream()
                .filter( vehicleIntegerEntry -> vehicleIntegerEntry.getKey().getRegistrationNumber()
                        .equals( registrationNumber ) )
                .map( vehicleIntegerEntry -> vehicleIntegerEntry.getValue() )
                .findAny()
                .get();
    }

    public List<Vehicle> getStatus() {
        return parkingMap.entrySet().stream()
                .map( vehicleIntegerEntry -> new Vehicle( vehicleIntegerEntry.getKey().getRegistrationNumber(),
                        vehicleIntegerEntry.getKey().getColor(), vehicleIntegerEntry.getValue() ) )
                .collect( Collectors.toList() );
    }

    public List<Vehicle> getRegistrationsByColor( String color ) {
        return parkingMap.entrySet().stream()
                .filter( vehicleIntegerEntry -> vehicleIntegerEntry.getKey().getColor().equals( color ) )
                .map( vehicleIntegerEntry -> vehicleIntegerEntry.getKey() )
                .collect( Collectors.toList() );
    }
}
