package com.tw.core.places;

import com.tw.core.Player;

/**
 * Created by pzzheng on 11/17/16.
 */
public class BombPlace extends ToolPlace{
    public BombPlace(Place basePlace) {
        super(basePlace);
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.stuckFor(Hospital.HOSPITAL_DAYS);
        return player.getGame().getMap().getHospital().comeHere(player);
    }
}
