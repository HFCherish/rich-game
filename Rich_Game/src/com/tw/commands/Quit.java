package com.tw.commands;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Quit implements Command {

    @Override
    public Player.Status execute(Player player) {
        player.getGame().end();
        return player.getStatus();
    }
}
