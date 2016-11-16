package com.tw.core.commands;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public interface Command {
    Player.Status execute(Player player);
}
