package com.tw.commands;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Help implements Command {

    @Override
    public Player.Status execute(Player player) {
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
