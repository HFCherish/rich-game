package com.tw.map;

import com.tw.commands.CommandFactory;
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

    public Player.Status passOthersEstate(Player player, Estate estate) {
        if (!player.isLucky()) {
            player.chargeFunds(estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1));
            if (player.getFunds() < 0) {
                return player.bankrupt();
            }
        }
        return player.endTurn();
    }

    public int getEmptyPrice() {
        return emptyPrice;
    }

    public EstateLevel getLevel() {
        return level;
    }

    public void upgrade() {
        level = EstateLevel.values()[level.ordinal() + 1];
    }

    public EstateType typeFor(Player player) {
        if (owner == null) return EstateType.EMPTY;
        if (owner.equals(player)) return EstateType.OWNER;
        return EstateType.OTHER;
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        Estate estate = this;
        EstateType type = estate.typeFor(player);
        if (type.equals(EstateType.OTHER)) {
            return passOthersEstate(player, estate);
        } else if (player.getFunds() < estate.getEmptyPrice()) {
            return player.endTurn();
        }
        if(type.equals(EstateType.OWNER))   player.setResponseCommand(CommandFactory.UpgradeEstate(this));
        return player.waitForResponse();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public enum EstateLevel {EMPTY, THATCH, FOREIGN_STYLE, SKYSCRAPER}

    public enum EstateType {OWNER, EMPTY, OTHER}
}
