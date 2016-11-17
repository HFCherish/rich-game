package com.tw.core;

import com.tw.core.assistentPower.Tool;
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

    public Place move(Place startPlace, int steps) {
        int startIndex = places.indexOf(startPlace);

        int direction = steps > 0 ? 1 : -1;
        for (int i=0; Math.abs(i) < places.size() && Math.abs(i) <= Math.abs(steps); i += direction) {
            int nextIndex = nextIndex(startIndex, i);
            Place nextPlace = places.get(nextIndex);
            Tool toolOnTheNextPlace = nextPlace.getToolOnThePlace();
            if(toolOnTheNextPlace != null) {
                nextPlace.removeTool();
                if(toolOnTheNextPlace.equals(Tool.BLOCK)) {
                    return new BlockPlace(nextPlace);
                }
                return new BombPlace(nextPlace);
            }

        }

        return places.get(nextIndex(startIndex, steps));
    }

    private int nextIndex(int start, int steps) {
        return (places.size() + (start + steps) % places.size()) % places.size();
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
        places.get(nextIndex(places.indexOf(start), steps)).putTool(Tool.BLOCK);
        return true;
    }

    public boolean putBomb(Place start, int steps) {
        places.get(nextIndex(places.indexOf(start), steps)).putTool(Tool.BOMB);
        return true;
    }

    public boolean useRobot(Place start) {
        return false;
    }
}
