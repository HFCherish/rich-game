package com.tw.player;

import com.tw.Dice;
import com.tw.giftHouse.*;
import com.tw.map.*;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolHouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Player {
    private final GameMap map;
    private Status status;
    private int funds;
    private Place currentPlace;
    private int points;
    private List<Tool> tools;
    private boolean hasLuckyGod;
    private int hospitalDays;

    public Player(GameMap map, int initialFund) {
        this.map = map;
        this.funds = initialFund;
        this.status = Status.WAIT_FOR_COMMAND;
        points = 0;
        hospitalDays = 0;
        tools = new ArrayList<>();
        hasLuckyGod = false;
    }

    public void roll(Dice dice) {
        currentPlace = map.move(currentPlace, dice.next());
        if (currentPlace instanceof Estate) {
            Estate estate = (Estate) this.currentPlace;
            Estate.EstateType type = estate.typeFor(this);
            if (type.equals(Estate.EstateType.OTHER)) {
                passOthersEstate(estate);
                return;
            }
            else if( type.equals(Estate.EstateType.EMPTY)) {
                if(funds < estate.getEmptyPrice()) {
                    status = Status.END_TURN;
                    return;
                }
                else {
                    status = Status.WAIT_FOR_RESPONSE;
                    return;
                }
            }
            else {
                if (funds < estate.getEmptyPrice()) {
                    status = Status.END_TURN;
                    return;
                }
                else {
                    status = Status.WAIT_FOR_RESPONSE;
                    return;
                }
            }
        }
        if( currentPlace instanceof ToolHouse) {
            if ((!((ToolHouse) currentPlace).canAffordWith(points) || tools.size() == 10)) {
                status = Status.END_TURN;
                return;
            }
            else {
                status = Status.WAIT_FOR_RESPONSE;
                return;
            }
        }
        if(currentPlace instanceof Block) {
            status = Status.END_TURN;
            return;
        }
        if(currentPlace instanceof Bomb) {
            status = Status.END_TURN;
            hospitalDays = 3;
            return;
        }
        status = Status.WAIT_FOR_RESPONSE;
    }

    private void passOthersEstate(Estate estate) {
        if (!isLucky()) {
            funds -= estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1);
            if(funds < 0) {
                status = Status.BANKRUPT;
                return;
            }
        }
        status = Status.END_TURN;
        return;
    }

    public Status getStatus() {
        return status;
    }

    public int getFunds() {
        return funds;
    }

    public void sayYes() {
        Estate estate = (Estate) currentPlace;
        if (estate.getOwner() == null) {
            funds -= estate.getEmptyPrice();
            estate.sellTo(this);
        } else if (estate.getOwner().equals(this)) {
            funds -= estate.getEmptyPrice();
            estate.upgrade();
        }
        status = Status.END_TURN;
    }

    public static Player createPlayerWith_Fund_Map(GameMap map, int initialFund) {
        return new Player(map, initialFund);
    }

    public void sayNo() {
        status = Status.END_TURN;
    }

    public void addPoint(int addedPoints) {
        points += addedPoints;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void buyTool(int toolIndex) {
        if (toolIndex == ToolHouse.QUIT_INDEX) {
            status = Status.END_TURN;
            return;
        }
        ToolHouse toolHouse = (ToolHouse) this.currentPlace;
        Tool toolById = toolHouse.getToolById(toolIndex);
        if (toolById != null) {
            tools.add(toolById);
            points -= toolById.getPoints();
            if (toolHouse.canAffordWith(points) && tools.size() < 10) {
                status = Status.WAIT_FOR_RESPONSE;
                return;
            }
        }
        status = Status.END_TURN;
    }

    public int getPoints() {
        return points;
    }

    public static Player createPlayerWith_Fund_Map_Tools(GameMap map, int initialFund10, Tool... tools) {
        Player player = createPlayerWith_Fund_Map(map, initialFund10);
        player.tools.addAll(Arrays.asList(tools));
        return player;
    }

    public void selectGift(int giftIndex_startFrom1) {
        GiftHouse giftHouse = (GiftHouse) currentPlace;
        Gift gift = giftHouse.getGift(giftIndex_startFrom1);
        if (gift != null) {
            if (gift instanceof PointCard) {
                points += gift.getValue();
            } else if (gift instanceof Fund) {
                funds += gift.getValue();
            } else if (gift instanceof LuckyGod) {
                hasLuckyGod = true;
            }
        }
        status = Status.END_TURN;
    }

    public boolean isLucky() {
        return hasLuckyGod;
    }

    public static Player createPlayerWith_Fund_Map_Lucky(GameMap map, int initialFund) {
        Player player = createPlayerWith_Fund_Map(map, initialFund);
        player.hasLuckyGod = true;
        return player;
    }

    public int hospitalDays() {
        return hospitalDays;
    }

    public enum Status {WAIT_FOR_COMMAND, END_TURN, BANKRUPT, WAIT_FOR_RESPONSE}
}
