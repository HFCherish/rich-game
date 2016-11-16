package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Estate implements Place{
    private int emptyPrice;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    @Override
    public Player.Status comeHere(Player player) {
        return Player.Status.WAIT_FOR_RESPONSE;
    }
}
