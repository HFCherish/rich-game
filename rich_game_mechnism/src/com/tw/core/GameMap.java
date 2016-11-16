package com.tw.core;

import com.tw.core.places.Place;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface GameMap {
    Place move(Place currentPlace, int steps);
}
