package com.tw.commands;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolType;

import java.util.HashMap;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Bomb implements Command {
    int steps;

    public Bomb(int steps) {
        this.steps = steps;
    }

    @Override
    public Player.Status execute(Player player) {
        HashMap<Tool, Integer> tools = player.getTools();
        ToolType toolType = ToolType.Bomb;
        if (tools.get(toolType) > 0 && (steps >= -10 && steps <= 10) && player.getMap().setTool(toolType, steps, player.getCurrentPlace()))
            player.removeTool(toolType);
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
