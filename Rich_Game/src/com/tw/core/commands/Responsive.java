package com.tw.core.commands;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public interface Responsive {
    Player.Status respond(Player player, Response response);
}
