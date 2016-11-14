package com.tw.map;

import com.tw.player.Player;

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

    public abstract void comeHere(Player player);
}
