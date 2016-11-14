package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BlockPlace extends ToolPlace {

    public BlockPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public void comeHere(Player player) {
        basePlace.comeHere(player);
        player.endTurn();
    }
}
