package com.tw.core.commands;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Quit implements Command {

    @Override
    public Player.Status execute(Player player) {
        player.getGame().endGame();
        return player.getStatus();
    }
}
