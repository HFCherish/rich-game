package com.tw.map;

import com.tw.toolHouse.Tool;

/**
 * Created by pzzheng on 11/12/16.
 */
public interface GameMap {
    Place move(Place start, int steps);

    boolean setTool(Tool tool, int steps, Place start);
}
