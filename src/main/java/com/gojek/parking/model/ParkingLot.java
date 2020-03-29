package com.gojek.parking.model;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private final int capacity;
    private ConcurrentHashMap <Integer,Vehicle> parkingMap ;

    public int getCapacity() {
        return capacity;
    }

    private void setParkingMap() {
        parkingMap = new ConcurrentHashMap<Integer, Vehicle>( capacity );
    }

    public ParkingLot( int capacity ) {
        this.capacity = capacity;
        setParkingMap();
    }

    public void park( Vehicle vehicle ) {
        parkingMap.put(1,vehicle);
    }

    public ConcurrentHashMap<Integer, Vehicle> getParkingMap() {
        return parkingMap;
    }
}
