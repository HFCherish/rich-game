package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.commands.CommandFactory;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Estate implements Place {
    private int emptyPrice;
    private Player owner;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        return estateType(player).action(player, this);
    }

    public EstateType estateType(Player player) {
        if (owner == null) return EstateType.EMPTY;
        if (owner.equals(player)) return EstateType.OWNER;
        return EstateType.OTHER;
    }

    public void sellTo(Player player) {
        owner = player;
    }

    public enum EstateType {
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
                return null;
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
}
