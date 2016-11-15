package com.tw.commands;

import com.tw.player.Player;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolHouse;

import java.util.HashMap;

/**
 * Created by pzzheng on 11/15/16.
 */
public class BuyTool implements Command, Responsive {
    @Override
    public Player.Status execute(Player player) {
        return null;
    }

    @Override
    public Player.Status respond(Player player, ResponseType responseType) {
        if (responseType.getNumber() == ToolHouse.QUIT_INDEX) {
            return player.endTurn();
        }
        ToolHouse toolHouse = (ToolHouse) player.getCurrentPlace();
        Tool toolById = (Tool) toolHouse.getItemByIndex(responseType.getNumber());

        if (toolById != null) {
            HashMap<Tool, Integer> tools = player.getTools();
            tools.compute(toolById, (k, v) -> v + 1);
            player.decreasePoints(toolById.getValue());
            if (toolHouse.canAffordWith(player.getPoints()) && tools.values().stream().reduce(0, (a, b) -> a + b) < 10) {
                return player.waitForResponse();
            }
        }
        return player.endTurn();
    }
}
