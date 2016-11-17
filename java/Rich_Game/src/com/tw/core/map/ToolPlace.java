package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public abstract class ToolPlace implements Place {
    Place basePlace;

    public ToolPlace(Place basePlace) {
        this.basePlace = basePlace;
    }

    public Place getBasePlace() {
        return basePlace;
    }

    public abstract Player.Status comeHere(Player player);
}
