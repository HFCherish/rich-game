package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public class BombPlace extends ToolPlace {
    public BombPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public void comeHere(Player player) {
        player.moveTo(player.getMap().getHospital());
        player.stuckFor(Hospital.HOSPITAL_DAYS);
        player.endTurn();
    }
}
