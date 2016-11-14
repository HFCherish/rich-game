package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class Hospital implements Place {
    @Override
    public void comeHere(Player player) {
        player.setCurrentPlace(this);
    }
}
