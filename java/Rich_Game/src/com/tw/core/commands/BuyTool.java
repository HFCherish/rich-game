package com.tw.core.commands;

import com.tw.core.player.Player;
import com.tw.core.toolHouse.Tool;
import com.tw.core.toolHouse.ToolHouse;

import java.util.HashMap;

/**
 * Created by pzzheng on 11/15/16.
 */
public class BuyTool implements Responsive {

    @Override
    public Player.Status respond(Player player, Response response) {
        if (response.getNumber() == ToolHouse.QUIT_INDEX) {
            return player.endTurn();
        }
        ToolHouse toolHouse = (ToolHouse) player.getCurrentPlace();
        Tool toolById = (Tool) toolHouse.getItemByIndex(response.getNumber());

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
