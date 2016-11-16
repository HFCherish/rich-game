package com.tw.core.commands;

import com.tw.core.map.Estate;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class BuyEstate implements Responsive {
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
    public Player.Status respond(Player player, Response response) {
        if(response.equals(Response.Yes)) {
            player.decreaseFunds(emptyEstate.getEmptyPrice());
            emptyEstate.setOwner(player);
            player.getEstates().add(emptyEstate);
        }
        return player.endTurn();

    }

    public Estate getEmptyEstate() {
        return emptyEstate;
    }
}
