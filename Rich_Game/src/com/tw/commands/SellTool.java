package com.tw.commands;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;

/**
 * Created by pzzheng on 11/15/16.
 */
public class SellTool implements Command {
    private Tool tool;

    public SellTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public Player.Status execute(Player player) {
        if (player.getTools().get(tool) > 0) {
            player.getTools().compute(tool, (k, v) -> v - 1);
            player.addPoint(tool.getValue());
        }
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
