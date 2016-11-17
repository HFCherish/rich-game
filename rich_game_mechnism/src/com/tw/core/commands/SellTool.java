package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.responses.Response;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/17/16.
 */
public class SellTool implements Command {
    private Tool tool;

    public SellTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public Player.Status execute(Player player) {
        if(player.getAsests().hasTool(tool)){
            player.getAsests().removeTool(tool);
            player.getAsests().addPoints(tool.getValue());
        }
        return player.waitForCommand();

    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }
}
