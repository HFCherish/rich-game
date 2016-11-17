package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class MagicHouse implements Place {
    @Override
    public Player.Status comeHere(Player player) {
        return player.endTurn();
    }
}
