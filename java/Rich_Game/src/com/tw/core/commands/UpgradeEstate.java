package com.tw.core.commands;

import com.tw.core.map.Estate;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class UpgradeEstate implements Responsive {
    private Estate estate;

    public UpgradeEstate(Estate estate) {
        this.estate = estate;
    }

    public Estate getEstate() {
        return estate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpgradeEstate that = (UpgradeEstate) o;

        return estate != null ? estate.equals(that.estate) : that.estate == null;

    }

    @Override
    public int hashCode() {
        return estate != null ? estate.hashCode() : 0;
    }

    @Override
    public Player.Status respond(Player player, Response response) {
        if( response.equals(Response.Yes)) {
            player.decreaseFunds(estate.getEmptyPrice());
            estate.upgrade();
        }
        return player.endTurn();
    }
}
