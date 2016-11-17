package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public interface Place {
    Player.Status comeHere(Player player);

}
