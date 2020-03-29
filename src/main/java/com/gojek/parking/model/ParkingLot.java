package com.gojek.parking.model;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private final int capacity;
    private ConcurrentHashMap <Vehicle,Integer> parkingMap ;

    public int getCapacity() {
        return capacity;
    }

    private void setParkingMap() {
        parkingMap = new ConcurrentHashMap<Vehicle, Integer>( capacity );
    }

    public ParkingLot( int capacity ) {
        this.capacity = capacity;
        setParkingMap();
    }

    public void park( Vehicle vehicle ) {
       if ( parkingMap.computeIfPresent(  vehicle,( vehicle1, slotNumber)->slotNumber +1 )==null ){
           parkingMap.put( vehicle,1 );
        }
    }

    public ConcurrentHashMap<Vehicle, Integer> getParkingMap() {
        return parkingMap;
    }
}
