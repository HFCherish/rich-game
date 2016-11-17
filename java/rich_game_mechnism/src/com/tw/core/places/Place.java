package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.assistentPower.Tool;

/**
 * Created by pzzheng on 11/16/16.
 */
public abstract class Place {
    protected Tool toolOnThePlace;

    public abstract Player.Status comeHere(Player player);

    public void putTool(Tool tool) {
        toolOnThePlace = tool;
    }

    public Tool getToolOnThePlace() {
        return toolOnThePlace;
    }

    public void removeTool() {
        toolOnThePlace = null;
    }
}
