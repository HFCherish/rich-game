package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.Player;
import com.tw.core.places.Estate;
import com.tw.core.responses.Response;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Roll implements Command {
    private Dice dice;

    public Roll(Dice dice) {
        this.dice = dice;
    }

    @Override
    public Player.Status execute(Player player) {
        return player.getGame().getMap().move(player.getCurrentPlace(), dice.next()).comeHere(player);
    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }

    public static class BuyEstate implements Command {
        public BuyEstate(Estate estate) {
        }

        @Override
        public Player.Status execute(Player player) {
            return null;
        }

        @Override
        public Player.Status respond(Response response, Player player) {
            return null;
        }
    }
}
