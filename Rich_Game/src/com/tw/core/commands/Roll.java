package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Roll implements Command {

    private Dice dice;

    public Roll(Dice dice) {
        this.dice = dice;
    }

    @Override
    public Player.Status execute(Player player) {
        return player.getMap().move(player.getCurrentPlace(), dice.next()).comeHere(player);
    }
}
