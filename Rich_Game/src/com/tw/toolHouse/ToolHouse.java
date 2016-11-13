package com.tw.toolHouse;

import com.tw.asest.AssistancePower;
import com.tw.house.House;
import com.tw.map.Place;
import com.tw.player.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/12/16.
 */
public class ToolHouse implements House {
    public static final int QUIT_INDEX = -1;
    private List<Tool> tools;

    public ToolHouse(Tool... tools) {
        this.tools = Arrays.asList(tools);
    }

    // TODO: 11/13/16 can use AssistancePower
    public Tool getItemByIndex(int toolIndex_StartFrom1) {
        return tools.get(toolIndex_StartFrom1 - 1);
    }

    public boolean canAffordWith(int points) {
        return tools.stream().anyMatch(tool -> tool.getPoints() <= points);
    }

    @Override
    public void comeHere(Player player) {
        if ((!canAffordWith(player.getPoints()) || player.getTools().size() == 10)) {
            player.endTurn();
        }
        else {
            player.waitForResponse();
        }
    }
}
