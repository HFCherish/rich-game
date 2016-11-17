package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Mineral extends Place {
    private int points;

    public Mineral(int points) {
        this.points = points;
    }


    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.getAsests().addPoints(points);
        return player.endTurn();
    }
}
