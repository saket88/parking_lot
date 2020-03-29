package com.gojek.parking.model;

import com.gojek.parking.exception.DuplicateVehicleException;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLot {
    private final int capacity;
    private ConcurrentHashMap <Vehicle,Integer> parkingMap ;

    public int getCapacity() {
        return capacity;
    }

    private void setParkingMap() {
        parkingMap = new ConcurrentHashMap<>( capacity );
    }

    public ParkingLot( int capacity ) {
        this.capacity = capacity;
        setParkingMap();
    }

    public Integer park( Vehicle vehicle ) throws DuplicateVehicleException, SizeLimitExceededException {
        validate( vehicle );
        if ( parkingMap.size()==0 ){
            parkingMap.putIfAbsent( vehicle ,1 );
            return 1;
        }
        int value = Collections.max( parkingMap.values() ) + 1;
        parkingMap.put( vehicle , value );
       return value;
    }

    private void validate( Vehicle vehicle ) throws DuplicateVehicleException, SizeLimitExceededException {
        if ( getSlot( vehicle ) !=null ){
            throw new DuplicateVehicleException("This is already present");
        }
        if ( parkingMap.size()==capacity ){
            throw new SizeLimitExceededException( "The parking space is Full" );
        }
    }

    private Integer getSlot( Vehicle vehicle ) {
        return parkingMap.get( vehicle );
    }

    public ConcurrentHashMap<Vehicle, Integer> getParkingMap() {
        return parkingMap;
    }

    public void leave( Integer vehicleSlot) {
        boolean isPresent=  parkingMap.values().remove( vehicleSlot );
         if ( !isPresent )
             throw new NoSuchElementException(" The slotv"+vehicleSlot +"vis not available");
    }

    public List<Integer> getSlotsByColor( String color ) {
        return parkingMap.entrySet().stream()
                .filter( vehicleIntegerEntry -> vehicleIntegerEntry.getKey().getColor().equals( color ) )
                .map( vehicleIntegerEntry -> vehicleIntegerEntry.getValue() )
                .collect( Collectors.toList() );
    }

}
