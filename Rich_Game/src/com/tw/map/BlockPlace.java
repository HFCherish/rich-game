package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BlockPlace implements Place {
    @Override
    public void comeHere(Player player) {
        player.endTurn();
    }
}
