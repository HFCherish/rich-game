package com.tw.core.commands;

import com.tw.core.GameHelp;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Help implements Command {

    @Override
    public Player.Status execute(Player player) {
        GameHelp.printHelpAsString();
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
