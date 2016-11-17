package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;
import com.tw.core.assistentPower.Tool;

/**
 * Created by pzzheng on 11/16/16.
 */
public class ToolHouse extends House {


    public ToolHouse(Tool... items) {
        super(items);
    }



    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        if(canNotAffordAnyToolWith(player.getAsests().getPoints())) {
            return player.endTurn();
        }
        player.setLastCommand(CommandFactory.BuyTool);
        return player.waitForResponse();
    }

    public boolean canNotAffordAnyToolWith(int points) {
        return items.stream().min((a, b) -> a.getValue() - b.getValue()).get().getValue() > points;
    }
}
