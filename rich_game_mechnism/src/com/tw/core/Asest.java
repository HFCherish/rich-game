package com.tw.core;

import com.tw.core.places.Estate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Asest {
    private int funds;
    private List<Estate> estates;
    private int points;

    public Asest() {
        estates = new ArrayList<>();
    }

    public void addFund(int bonus) {
        funds = bonus;
    }

    public int getFunds() {
        return funds;
    }

    public List<Estate> getEstates() {
        return estates;
    }

    public void decreaseFunds(int charge) {
        funds -= charge;
    }

    public void addEstate(Estate estate) {
        estates.add(estate);
    }

    public void addPoints(int value) {
        points += value;
    }

    public int getPoints() {
        return points;
    }
}
