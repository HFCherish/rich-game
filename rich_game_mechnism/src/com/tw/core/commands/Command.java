package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.responses.Response;

/**
 * Created by pzzheng on 11/16/16.
 */
public interface Command {
    Player.Status execute(Player player);

    Player.Status respond(Response response, Player player);
}
