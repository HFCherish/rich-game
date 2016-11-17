package com.tw.core;

import com.sun.tools.javac.main.Option;
import com.tw.core.places.Estate;
import com.tw.core.tools.AssistentPower;
import com.tw.core.tools.Gift;
import com.tw.core.tools.Tool;

import java.util.*;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Asest {
    private int funds;
    private List<Estate> estates;
    private int points;
    private Map<Tool, Integer> tools;

    public Asest() {
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

    public boolean hasTool(Tool tool) {
        tools.compute(tool, (k,v) -> v == null ? 0 : v);
        return tools.get(tool) > 0;
    }

    public void addTool(Tool tool) {
        tools.compute(tool, (k,v) -> v == null ? 1 : v+1);
        points -= tool.getValue();
    }

    public void useTool(Tool tool) {
        tools.compute(tool, (k,v) -> v-1);
    }

    public boolean hasEstate(Estate estate) {
        return estates.contains(estate);
    }

    public void removeEstate(Estate estate) {
        estates.remove(estate);
    }
}
