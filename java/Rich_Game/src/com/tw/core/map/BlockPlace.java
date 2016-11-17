package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BlockPlace extends ToolPlace {

    public BlockPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public Player.Status comeHere(Player player) {
        basePlace.comeHere(player);
        return player.endTurn();
    }
}
