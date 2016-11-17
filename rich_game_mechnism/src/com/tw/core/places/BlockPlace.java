package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/17/16.
 */
public class BlockPlace extends ToolPlace{
    public BlockPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public Player.Status comeHere(Player player) {
        return basePlace.comeHere(player);
    }
}
