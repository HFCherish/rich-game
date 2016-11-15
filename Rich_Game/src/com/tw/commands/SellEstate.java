package com.tw.commands;

import com.tw.map.Estate;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class SellEstate implements Command {
    private Estate estate;

    public SellEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public Player.Status execute(Player player) {
        if (player.getEstates().contains(estate)) {
            player.addFunds(estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) * 2);
            player.getEstates().remove(estate);
            estate.setOwner(null);
        }
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
