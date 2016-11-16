package com.tw.core.toolHouse;

import com.tw.core.commands.ResponsiveFactory;
import com.tw.core.house.House;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public class ToolHouse extends House {
    public static final int QUIT_INDEX = -1;

    public ToolHouse(Tool... tools) {
        super(tools);
    }

    public boolean canAffordWith(int points) {
        return items.stream().anyMatch(tool -> tool.getValue() <= points);
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        if ((!canAffordWith(player.getPoints()) || player.getTools().values().stream().reduce(0, (a, b) -> a+b) == 10)) {
            return player.endTurn();
        }
        else {
            player.setResponseCommand(ResponsiveFactory.BuyTool);
            return player.waitForResponse();
        }
    }
}
