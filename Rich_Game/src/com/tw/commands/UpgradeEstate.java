package com.tw.commands;

import com.tw.map.Estate;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class UpgradeEstate implements Responsive {
    private Estate estate;

    public UpgradeEstate(Estate estate) {
        this.estate = estate;
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
    public Player.Status respond(Player player, ResponseType responseType) {
        player.decreaseFunds(estate.getEmptyPrice());
        estate.upgrade();
        return player.endTurn();
    }
}
