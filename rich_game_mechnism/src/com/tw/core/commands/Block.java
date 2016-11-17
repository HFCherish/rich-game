package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.responses.Response;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Block implements Command {
    private int steps;

    public Block(int steps) {
        this.steps = steps;
    }

    @Override
    public Player.Status execute(Player player) {
        if(player.getAsests().hasTool(Tool.BLOCK)){

        }
        return player.waitForCommand();
    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }
}
