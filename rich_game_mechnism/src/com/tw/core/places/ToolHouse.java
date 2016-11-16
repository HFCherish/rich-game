package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/16/16.
 */
public class ToolHouse implements Place {
    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.setLastCommand(CommandFactory.BuyTool);
        return Player.Status.WAIT_FOR_RESPONSE;
    }
}
