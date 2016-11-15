package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class MineralEstate implements Place{
    private int points;

    public MineralEstate(int points) {
        this.points = points;
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.addPoint(points);
        return player.endTurn();
    }
}
