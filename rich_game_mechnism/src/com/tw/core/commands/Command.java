package com.tw.core.commands;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface Command {
    Player.Status execute(Player player);
}
