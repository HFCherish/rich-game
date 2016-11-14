package com.tw.map;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;

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
        Place place = null;
        int direction = steps >= 0 ? 1 : -1;
        for (int i = direction; i != steps + direction; i += direction) {
            int nextPlaceIndex = nextPlaceIndex(start, i);
            place = places.get(nextPlaceIndex);
            if (place instanceof ToolPlace) {
                places.set(nextPlaceIndex, ((ToolPlace)place).getBasePlace());
                break;
            }
        }
        return place;
    }

    private int nextPlaceIndex(Place start, int steps) {
        int size = places.size();
        return (size + (places.indexOf(start) + steps) % size) % size;
    }


    @Override
    public boolean setTool(Tool tool, int steps, Place start) {
        Place placeToSetTool = places.get(nextPlaceIndex(start, steps));
        if(placeToSetTool instanceof BombPlace || placeToSetTool instanceof BlockPlace) {
            return false;
        }
        if(players.stream().anyMatch(player -> player.getCurrentPlace().equals(placeToSetTool))) {
            return false;
        }
        places.set(places.indexOf(placeToSetTool), new BombPlace(placeToSetTool));
        return true;
    }

    @Override
    public Place getHospital() {
        return places.stream().filter(place -> place instanceof Hospital).findAny().get();
    }

}
