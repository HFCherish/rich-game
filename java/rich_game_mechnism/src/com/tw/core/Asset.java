package com.tw.core;

import com.tw.core.places.Estate;
import com.tw.core.assistentPower.Tool;

import java.util.*;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Asset {
    private int funds;
    private List<Estate> estates;
    private int points;
    private Map<Tool, Integer> tools;

    public Asset() {
        estates = new ArrayList<>();
        tools = new HashMap();
    }

    public void addFund(int bonus) {
        funds += bonus;
    }

    public int getFunds() {
        return funds;
    }

    public List<Estate> getEstates() {
        return estates;
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

    public void addTool(Tool tool) {
        tools.compute(tool, (k,v) -> v == null ? 1 : v+1);
        points -= tool.getValue();
    }

    public void removeTool(Tool tool) {
        tools.compute(tool, (k,v) -> v-1);
    }

    public boolean hasEstate(Estate estate) {
        return estates.contains(estate);
    }

    public void removeEstate(Estate estate) {
        estates.remove(estate);
    }

    public int getToolCount(Tool tool) {
        return tools.compute(tool, (k,v) -> v == null ? 0 : v);
    }
}
