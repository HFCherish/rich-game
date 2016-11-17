package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.places.Estate;
import com.tw.core.responses.Response;

/**
 * Created by pzzheng on 11/17/16.
 */
public class SellEstate implements Command {
    private Estate estate;

    public SellEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public Player.Status execute(Player player) {
        if(player.getAsests().hasEstate(estate)){
            player.getAsests().addFund(estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) * 2);
            player.getAsests().removeEstate(estate);
            estate.beEmpty();
        }
        return player.waitForCommand();

    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }
}
