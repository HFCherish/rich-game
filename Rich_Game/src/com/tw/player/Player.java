package com.tw.player;

import com.tw.Dice;
import com.tw.asest.AssistancePower;
import com.tw.giftHouse.*;
import com.tw.house.House;
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
    private List<AssistancePower> tools;
    private boolean hasLuckyGod;
    private int hospitalDays;
    private int prisonDays;
    private int stuckDays;

    public Player(GameMap map, int initialFund) {
        this.map = map;
        this.funds = initialFund;
        this.status = Status.WAIT_FOR_COMMAND;
        points = 0;
        stuckDays = 0;
        hospitalDays = 0;
        prisonDays = 0;
        tools = new ArrayList<>();
        hasLuckyGod = false;
    }

    public void roll(Dice dice) {
        currentPlace = map.move(currentPlace, dice.next());
        currentPlace.comeHere(this);
    }

    public void waitForResponse() {
        status = Status.WAIT_FOR_RESPONSE;
    }

    public void endTurn() {
        status = Status.END_TURN;
    }

    public void bankrupt() {
        status = Status.BANKRUPT;
    }

    public void chargeFunds(int fees) {
        funds -= fees;
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
        endTurn();
    }

    public static Player createPlayerWith_Fund_Map(GameMap map, int initialFund) {
        return new Player(map, initialFund);
    }

    public void sayNo() {
        endTurn();
    }

    public void addPoint(int addedPoints) {
        points += addedPoints;
    }

    public List<AssistancePower> getTools() {
        return tools;
    }

    public void buyTool(int toolIndex) {
        if (toolIndex == ToolHouse.QUIT_INDEX) {
            endTurn();
            return;
        }
        ToolHouse toolHouse = (ToolHouse) this.currentPlace;
        Tool toolById = toolHouse.getItemByIndex(toolIndex);
        if (toolById != null) {
            tools.add(toolById);
            points -= toolById.getPoints();
            if (toolHouse.canAffordWith(points) && tools.size() < 10) {
                waitForResponse();
                return;
            }
        }
        endTurn();
    }

    public int getPoints() {
        return points;
    }

    public static Player createPlayerWith_Fund_Map_Tools(GameMap map, int initialFund10, AssistancePower... tools) {
        Player player = createPlayerWith_Fund_Map(map, initialFund10);
        player.tools.addAll(Arrays.asList(tools));
        return player;
    }

    public void selectGift(int giftIndex_startFrom1) {
        House giftHouse = (House) currentPlace;
        AssistancePower gift = giftHouse.getItemByIndex(giftIndex_startFrom1);
        if (gift != null) {
            if (gift instanceof PointCard) {
                points += ((PointCard) gift).getValue();
            } else if (gift instanceof Fund) {
                funds += ((Fund) gift).getValue();
            } else if (gift instanceof LuckyGod) {
                hasLuckyGod = true;
            }
        }
        endTurn();
    }

    public boolean isLucky() {
        return hasLuckyGod;
    }

    public static Player createPlayerWith_Fund_Map_Lucky(GameMap map, int initialFund) {
        Player player = createPlayerWith_Fund_Map(map, initialFund);
        player.hasLuckyGod = true;
        return player;
    }

    public void stuckFor(int days) {
        stuckDays = days;
    }

    public int getStuckDays() {
        return stuckDays;
    }

    public enum Status {WAIT_FOR_COMMAND, END_TURN, BANKRUPT, WAIT_FOR_RESPONSE}
}
