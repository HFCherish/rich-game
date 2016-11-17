package com.tw.core.map;

import com.tw.core.commands.ResponsiveFactory;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Estate implements Place {
    private int emptyPrice;
    private int index;
    private Player owner;
    private EstateLevel level;

    public Estate(int emptyPrice, int index) {
        this.emptyPrice = emptyPrice;
        this.index = index;
        level = EstateLevel.EMPTY;
    }

    public int getIndex() {
        return index;
    }

    public Player.Status passOthersEstate(Player player, Estate estate) {
        if (!player.isLucky()) {
            player.decreaseFunds(estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1));
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
        if(type.equals(EstateType.OWNER))   player.setResponseCommand(ResponsiveFactory.UpgradeEstate(this));
        else player.setResponseCommand(ResponsiveFactory.BuyEstate(this));
        return player.waitForResponse();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public enum EstateLevel {EMPTY, THATCH, FOREIGN_STYLE, SKYSCRAPER}

    public enum EstateType {OWNER, EMPTY, OTHER}
}
