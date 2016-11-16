package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Prison implements Place {

    public static final int PRISON_DAYS = 2;

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.stuckFor(PRISON_DAYS);
        return player.endTurn();
    }
}
