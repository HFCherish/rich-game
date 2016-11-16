package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;
import com.tw.core.tools.AssistentPower;

/**
 * Created by pzzheng on 11/17/16.
 */
public class GiftHouse extends House{

    public GiftHouse(AssistentPower... items) {
        super(items);
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.setLastCommand(CommandFactory.SelectGift);
        return player.waitForResponse();
    }
}
