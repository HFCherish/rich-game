package com.tw.map;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Block implements Place, Tool{
    @Override
    public void comeHere(Player player) {
        player.endTurn();
    }

    @Override
    public int getPoints() {
        return 0;
    }
}
