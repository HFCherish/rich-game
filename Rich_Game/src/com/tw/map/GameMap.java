package com.tw.map;

/**
 * Created by pzzheng on 11/12/16.
 */
public interface GameMap {
    Place move(Place start, int steps);
}