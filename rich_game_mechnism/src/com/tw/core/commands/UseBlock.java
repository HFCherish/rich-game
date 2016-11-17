package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.responses.Response;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/17/16.
 */
public class UseBlock implements Command {
    private int steps;

    public UseBlock(int steps) {
        this.steps = steps;
    }

    @Override
    public Player.Status execute(Player player) {
        if(player.getAsests().getToolCount(Tool.BLOCK) > 0 && player.getGame().getMap().putBlock(player.getCurrentPlace(), steps)){
            player.getAsests().removeTool(Tool.BLOCK);
        }
        return player.waitForCommand();
    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }
}
