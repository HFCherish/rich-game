package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Estate extends Place {
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
        level = Level.values()[level.ordinal() + 1];
    }

    public Player getOwner() {
        return owner;
    }

    public void beEmpty() {
        setOwner(null);
        level = Level.EMPTY;
    }

    public enum Type {
        EMPTY {
            @Override
            Player.Status action(Player player, Estate estate) {
                if (player.getAsests().getFunds() < estate.emptyPrice) return player.endTurn();
                player.setLastCommand(CommandFactory.BuyEstate(estate));
                return player.waitForResponse();
            }
        }, OTHER {
            @Override
            Player.Status action(Player player, Estate estate) {
                if (!player.isLucky() && !estate.getOwner().isStucked()) {
                    int charge = (int) (1.0 * estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) / 2);
                    player.getAsests().addFund(-charge);
                    if (player.getAsests().getFunds() < 0) return player.bankrupt();
                }
                return player.endTurn();
            }
        }, OWNER {
            @Override
            Player.Status action(Player player, Estate estate) {
                if (player.getAsests().getFunds() < estate.emptyPrice) return player.endTurn();
                player.setLastCommand(CommandFactory.UpgradeEstate(estate));
                return player.waitForResponse();
            }
        };

        abstract Player.Status action(Player player, Estate estate);
    }

    public enum Level {EMPTY, THATCH, FOREIGN_STYLE, SKYSCRAPER}
}
