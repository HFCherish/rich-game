package com.tw.core;

import com.tw.core.places.Hospital;
import com.tw.core.places.Place;
import com.tw.core.places.Prison;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface GameMap {
    Place move(Place currentPlace, int steps);

    Hospital getHospital();

    Prison getPrison();
}
