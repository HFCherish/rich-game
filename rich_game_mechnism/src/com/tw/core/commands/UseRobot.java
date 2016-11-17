package com.tw.core.commands;

import com.tw.core.Player;
import com.tw.core.responses.Response;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/17/16.
 */
public class UseRobot implements Command {
    @Override
    public Player.Status execute(Player player) {
        if(player.getAsests().getToolCount(Tool.ROBOT_DULL) > 0 && player.getGame().getMap().useRobot(player.getCurrentPlace())){
            player.getAsests().removeTool(Tool.ROBOT_DULL);
        }
        return player.waitForCommand();
    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }
}
