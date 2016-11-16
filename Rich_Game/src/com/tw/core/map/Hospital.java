package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class Hospital implements Place {
    public static final int HOSPITAL_DAYS = 3;
    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        return player.endTurn();
    }
}
