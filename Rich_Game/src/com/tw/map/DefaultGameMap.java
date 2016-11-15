package com.tw.map;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/14/16.
 */
public class DefaultGameMap implements GameMap {
    private final int width;
    private final int height;
    List<Place> places;
    List<Player> players;

    public DefaultGameMap(int width, int height, Place... places) {
        this.width = width;
        this.height = height;
        this.places = Arrays.asList(places);
        players = new ArrayList<>();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public Place move(Place start, int steps) {
        Place place;
        int direction = steps >= 0 ? 1 : -1;
        int startIndex = places.indexOf(start);
        for (int i = direction; Math.abs(i) <= Math.abs(steps) && Math.abs(i) <= places.size(); i += direction) {
            place = removeNextTool(startIndex, i);
            if (place != null) return place;
        }
        return places.get(nextPlaceIndex(steps, startIndex));
    }

    private Place removeNextTool(int startIndex, int steps) {
        Place place;
        int nextPlaceIndex = nextPlaceIndex(steps, startIndex);
        place = places.get(nextPlaceIndex);
        if (place instanceof ToolPlace) {
            places.set(nextPlaceIndex, ((ToolPlace) place).getBasePlace());
            return place;
        }
        return null;
    }

    private int nextPlaceIndex(int steps, int startIndex) {
        int size = places.size();
        return (size + (startIndex + steps) % size) % size;
    }


    @Override
    public boolean setTool(Tool tool, int steps, Place start) {
        if (tool.equals(ToolType.RobotDull)) {
            useRobot(start);
            return true;
        }
        Place placeToSetTool = places.get(nextPlaceIndex(steps, places.indexOf(start)));
        if (placeToSetTool instanceof ToolPlace) {
            return false;
        }
        if (players.stream().anyMatch(player -> player.getCurrentPlace().equals(placeToSetTool))) {
            return false;
        }
        if (tool.equals(ToolType.Bomb)) {
            places.set(places.indexOf(placeToSetTool), new BombPlace(placeToSetTool));
        } else if (tool.equals(ToolType.Block)) {
            places.set(places.indexOf(placeToSetTool), new BlockPlace(placeToSetTool));
        }
        return true;
    }

    public void useRobot(Place start) {
        int startIndex = places.indexOf(start);
        for(int i = 1; i<= Tool.RANGE && i<=places.size(); i++ ) {
            removeNextTool(startIndex, i);
            removeNextTool(startIndex, -i);
        }
    }

    @Override
    public Place getHospital() {
        return places.stream().filter(place -> place instanceof Hospital).findAny().get();
    }

}
