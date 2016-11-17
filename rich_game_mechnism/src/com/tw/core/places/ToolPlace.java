package com.tw.core.places;

/**
 * Created by pzzheng on 11/17/16.
 */
public abstract class ToolPlace implements Place{
    Place basePlace;

    public ToolPlace(Place basePlace) {
        this.basePlace = basePlace;
    }

    public Place getBasePlace() {
        return basePlace;
    }
}
