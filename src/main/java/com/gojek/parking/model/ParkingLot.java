package com.gojek.parking.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public Integer park( Vehicle vehicle ) {
        if ( parkingMap.size()==0 ){
            parkingMap.putIfAbsent( vehicle ,1 );
            return parkingMap.get( vehicle);
        }

        parkingMap.put( vehicle , Collections.max( parkingMap.values() ) +1 );
       return parkingMap.get( vehicle );
    }

    public ConcurrentHashMap<Vehicle, Integer> getParkingMap() {
        return parkingMap;
    }

    public void leave( Integer vehicleSlot) {
        parkingMap.values().remove( vehicleSlot );
    }
}
