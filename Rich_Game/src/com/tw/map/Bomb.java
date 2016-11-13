package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Bomb implements Place{
    @Override
    public void comeHere(Player player) {
        player.endTurn();
        player.goToHospital();
    }
}
