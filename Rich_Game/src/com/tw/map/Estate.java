package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Estate implements Place{
    private int emptyPrice;
    private Player owner;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    public Player getOwner() {
        return owner;
    }

    public void sellTo(Player currentPlayer) {
        this.owner = currentPlayer;
    }
}
