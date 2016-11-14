package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BombPlace implements Place {
    @Override
    public void comeHere(Player player) {
        player.endTurn();
        player.stuckFor(3);
    }
}
