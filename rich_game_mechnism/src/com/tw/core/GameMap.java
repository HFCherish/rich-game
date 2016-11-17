package com.tw.core;

import com.tw.core.places.Hospital;
import com.tw.core.places.Place;
import com.tw.core.places.Prison;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface GameMap {
    Place move(Place currentPlace, int steps);

    Hospital getHospital();

    Prison getPrison();

    boolean putBlock(Tool block);
}
