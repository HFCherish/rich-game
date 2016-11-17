package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.assistentPower.Tool;

/**
 * Created by pzzheng on 11/17/16.
 */
public class BlockPlace extends ToolPlace{
    public BlockPlace(Place basePlace) {
        super(basePlace);
        toolOnThePlace = Tool.BLOCK;
    }

    @Override
    public Player.Status comeHere(Player player) {
        return basePlace.comeHere(player);
    }
}
