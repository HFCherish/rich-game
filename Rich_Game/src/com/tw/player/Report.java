package com.tw.player;

import com.tw.map.Estate;
import com.tw.toolHouse.Tool;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pzzheng on 11/13/16.
 */
public interface Report {
    String reportAsString(int funds, int points, List<Estate> estates, HashMap<Tool, Integer> tools);
}
