package com.tw.map;

import com.tw.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Estate implements Place {
    private int emptyPrice;
    private Player owner;
    private EstateLevel level;

    public Estate(int emptyPrice) {
        this.emptyPrice = emptyPrice;
        level = EstateLevel.EMPTY;
    }

    public void passOthersEstate(Player player, Estate estate) {
        if (!player.isLucky()) {
            player.chargeFunds(estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1));
            if(player.getFunds() < 0) {
                player.bankrupt();
                return;
            }
        }
        player.endTurn();
        return;
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    public Player getOwner() {
        return owner;
    }

    public void sellTo(Player currentPlayer) {
        this.owner = currentPlayer;
    }

    public EstateLevel getLevel() {
        return level;
    }

    public void upgrade() {
        level = EstateLevel.values()[level.ordinal() + 1];
    }

    public EstateType typeFor(Player player) {
        if(owner == null)   return EstateType.EMPTY;
        if(owner.equals(player))   return EstateType.OWN;
        return EstateType.OTHER;
    }

    @Override
    public void comeHere(Player player) {
        Estate estate = this;
        EstateType type = estate.typeFor(player);
        if (type.equals(EstateType.OTHER)) {
            passOthersEstate(player, estate);
        }
        else if (player.getFunds() < estate.getEmptyPrice()) {
            player.endTurn();
        }
        else {
            player.waitForResponse();
        }
    }

    public enum EstateLevel {EMPTY, THATCH}

    public enum EstateType {OWN, EMPTY, OTHER}
}
