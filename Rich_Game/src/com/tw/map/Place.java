package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public interface Place {
    Player.Status comeHere(Player player);

}
