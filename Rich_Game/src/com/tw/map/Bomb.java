package com.tw.map;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Bomb implements Place, Tool{
    @Override
    public void comeHere(Player player) {
        player.endTurn();
        player.stuckFor(3);
    }

    @Override
    public int getPoints() {
        return 0;
    }
}
