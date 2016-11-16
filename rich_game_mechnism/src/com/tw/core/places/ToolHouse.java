package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;
import com.tw.core.tools.Tool;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class ToolHouse implements Place {
    private final List<Tool> tools;

    public ToolHouse(Tool... tools) {
        this.tools = Arrays.asList(tools);
    }



    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        if(canNotAffordAnyToolWith(player.getAsests().getPoints())) {
            return Player.Status.WAIT_FOR_TURN;
        }
        player.setLastCommand(CommandFactory.BuyTool);
        return Player.Status.WAIT_FOR_RESPONSE;
    }

    public boolean canNotAffordAnyToolWith(int points) {
        return tools.stream().min((a, b) -> a.getValue() - b.getValue()).get().getValue() > points;
    }
}
