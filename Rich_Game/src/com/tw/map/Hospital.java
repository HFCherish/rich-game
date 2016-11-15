package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class Hospital implements Place {
    public static final int HOSPITAL_DAYS = 3;
    @Override
    public void comeHere(Player player) {
        player.setCurrentPlace(this);
    }
}
