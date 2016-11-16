package com.tw.core.map;

import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BombPlace extends ToolPlace {
    public BombPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(player.getMap().getHospital());
        player.stuckFor(Hospital.HOSPITAL_DAYS);
        return player.endTurn();
    }
}
