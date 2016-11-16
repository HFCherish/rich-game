package com.tw.core.map;

import com.tw.core.player.Player;
import com.tw.core.toolHouse.Tool;

import java.util.List;

/**
 * Created by pzzheng on 11/12/16.
 */
public interface GameMap {
    void addPlayer(Player player);

    Place move(Place start, int steps);

    boolean setTool(Tool tool, int steps, Place start);

    Place getHospital();

    int getHeight();

    int getWidth();

    List<Place> getPlaces();

    Estate getEstate(int index);
}
