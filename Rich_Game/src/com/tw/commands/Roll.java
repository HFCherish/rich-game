package com.tw.commands;

import com.tw.Dice;
import com.tw.player.Player;

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
