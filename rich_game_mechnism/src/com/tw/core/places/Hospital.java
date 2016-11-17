package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Hospital implements Place{
    private static int HOSPITAL_DAYS = 2;
    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        return player.endTurn();
    }
}
