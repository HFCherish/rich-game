package com.tw.core;

import com.tw.core.places.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class GameMap {
    private List<Place> places;
    private static Hospital hospital;
    private static Prison prison;

    public GameMap(Place... places) {
        this.places = Arrays.asList(places);
    }

    public Place move(Place currentPlace, int steps) {
        int currentPlaceIndex = places.indexOf(currentPlace);
        int nextIndex = (places.size() + (currentPlaceIndex + steps) % places.size()) % places.size();
        return places.get(nextIndex);
    }

    public Hospital getHospital() {
        if(hospital == null) hospital = (Hospital)(places.stream().filter(place -> place instanceof Hospital).findAny().get());
        return hospital;
    }

    public Prison getPrison() {
        if(prison == null) prison = (Prison) (places.stream().filter(place -> place instanceof Prison).findAny().get());
        return prison;
    }

    public boolean putBlock(Place start, int steps) {
        return false;
    }

    public boolean putBomb(Place start, int steps) {
        return false;
    }

    public boolean useRobot(Place start) {
        return false;
    }
}
