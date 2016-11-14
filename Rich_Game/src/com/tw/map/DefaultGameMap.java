package com.tw.map;

import com.tw.toolHouse.Tool;

/**
 * Created by pzzheng on 11/14/16.
 */
public class DefaultGameMap implements GameMap {
    private final int width;
    private final int height;

    public DefaultGameMap(int width, int height) {

        this.width = width;
        this.height = height;
    }

    @Override
    public Place move(Place start, int steps) {
        return null;
    }

    @Override
    public boolean setTool(Tool tool, int steps, Place start) {
        return false;
    }
}
