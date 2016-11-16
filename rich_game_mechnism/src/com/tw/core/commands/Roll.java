package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.Player;
import com.tw.core.places.Estate;
import com.tw.core.places.ToolHouse;
import com.tw.core.responses.Response;
import com.tw.core.tools.Tool;

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
        private Estate estate;

        public BuyEstate(Estate estate) {
            this.estate = estate;
        }

        @Override
        public Player.Status execute(Player player) {
            return null;
        }

        @Override
        public Player.Status respond(Response response, Player player) {
            if(response.equals(Response.Yes)) {
                player.getAsests().decreaseFunds(estate.getEmptyPrice());
                player.getAsests().addEstate(estate);
                estate.setOwner(player);
            }
            return Player.Status.WAIT_FOR_TURN;
        }
    }

    public static class UpgradeEstate implements Command{
        private Estate estate;

        public UpgradeEstate(Estate estate) {
            this.estate = estate;
        }

        @Override
        public Player.Status execute(Player player) {
            return null;
        }

        @Override
        public Player.Status respond(Response response, Player player) {
            if(response.equals(Response.Yes)) {
                player.getAsests().decreaseFunds(estate.getEmptyPrice());
                estate.upgrade();
            }
            return Player.Status.WAIT_FOR_TURN;
        }
    }

    public static class BuyTool implements Command{

        @Override
        public Player.Status execute(Player player) {
            return null;
        }

        @Override
        public Player.Status respond(Response response, Player player) {
            if(response.equals(Response.Quit)) {
                return Player.Status.WAIT_FOR_TURN;
            }
            player.getAsests().addTool(response.getTool());
            if(((ToolHouse)player.getCurrentPlace()).canNotAffordAnyToolWith(player.getAsests().getPoints())) {
                return Player.Status.WAIT_FOR_TURN;
            }
            return Player.Status.WAIT_FOR_RESPONSE;
        }
    }
}
