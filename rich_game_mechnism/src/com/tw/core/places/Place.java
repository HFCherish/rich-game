package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface Place {
    Player.Status comeHere(Player player);
}
