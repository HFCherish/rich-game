package com.tw.commands;

import com.tw.map.Estate;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class BuyEstate implements Command, Responsive {
    private Estate emptyEstate;

    public BuyEstate(Estate emptyEstate) {
        this.emptyEstate = emptyEstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuyEstate buyEstate = (BuyEstate) o;

        return emptyEstate != null ? emptyEstate.equals(buyEstate.emptyEstate) : buyEstate.emptyEstate == null;
    }

    @Override
    public int hashCode() {
        return emptyEstate != null ? emptyEstate.hashCode() : 0;
    }

    @Override
    public Player.Status execute(Player player) {
        return null;
    }

    @Override
    public Player.Status response(Player player, ResponseType responseType) {
        return null;
    }
}
