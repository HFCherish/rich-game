package com.tw.commands;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public interface Command {
    Player.Status execute(Player player);
}
