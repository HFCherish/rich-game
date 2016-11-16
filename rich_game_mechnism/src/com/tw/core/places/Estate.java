package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Estate implements Place {
    private int emptyPrice;
    private Player owner;
    private Level level;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
        level = Level.EMPTY;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        return estateType(player).action(player, this);
    }

    public Type estateType(Player player) {
        if (owner == null) return Type.EMPTY;
        if (owner.equals(player)) return Type.OWNER;
        return Type.OTHER;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Level getLevel() {
        return level;
    }

    public void upgrade() {
        level =  Level.values()[level.ordinal() + 1];
    }

    public enum Type {
        EMPTY {
            @Override
            Player.Status action(Player player, Estate estate) {
                if (player.getAsests().getFunds() < estate.emptyPrice) return Player.Status.WAIT_FOR_TURN;
                player.setLastCommand(CommandFactory.BuyEstate(estate));
                return Player.Status.WAIT_FOR_RESPONSE;
            }
        }, OTHER {
            @Override
            Player.Status action(Player player, Estate estate) {
                int charge = (int)(1.0 * estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) / 2);
                if (player.getAsests().getFunds() < charge) return Player.Status.BANKRUPT;
                player.getAsests().decreaseFunds(charge);
                return Player.Status.WAIT_FOR_TURN;
            }
        }, OWNER {
            @Override
            Player.Status action(Player player, Estate estate) {
                if (player.getAsests().getFunds() < estate.emptyPrice) return Player.Status.WAIT_FOR_TURN;
                player.setLastCommand(CommandFactory.UpgradeEstate(estate));
                return Player.Status.WAIT_FOR_RESPONSE;
            }
        };
        abstract Player.Status action(Player player, Estate estate);
    }

    public enum Level {EMPTY, THATCH}
}
