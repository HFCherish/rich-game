package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Prison implements Place{
    private static int PRISON_DAYS = 2;

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.stuckFor(PRISON_DAYS);
        return player.endTurn();
    }
}
