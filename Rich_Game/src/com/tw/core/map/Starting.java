package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class Starting implements Place {

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        return player.endTurn();
    }
}
