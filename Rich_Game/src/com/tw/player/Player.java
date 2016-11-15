package com.tw.player;

import com.tw.Dice;
import com.tw.Game;
import com.tw.GameHelp;
import com.tw.asest.AssistancePower;
import com.tw.giftHouse.*;
import com.tw.house.House;
import com.tw.map.*;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolHouse;
import com.tw.toolHouse.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    HashMap<Tool, Integer> tools;
    List<Estate> estates;
    private boolean hasLuckyGod;
    private int stuckDays;
    private Game game;

    public Player(GameMap map, int initialFund) {
        this.map = map;
        this.map.addPlayer(this);
        funds = initialFund;
        this.status = Status.WAIT_FOR_COMMAND;
        points = 0;
        stuckDays = 0;
        tools = new HashMap<>();
        Arrays.stream(ToolType.values()).forEach(toolType -> tools.compute(toolType, (k, v) -> 0));
        estates = new ArrayList<>();
        hasLuckyGod = false;
    }

    public void roll(Dice dice) {
        currentPlace = map.move(currentPlace, dice.next());
        currentPlace.comeHere(this);
    }

    public void setCurrentPlace(Place place) {
        this.currentPlace = place;
    }

    public void waitForResponse() {
        status = Status.WAIT_FOR_RESPONSE;
    }

    public void endTurn() {
        status = Status.WAIT_FOR_TURN;
        game.inform(status);
    }

    public void bankrupt() {
        status = Status.BANKRUPT;
        game.inform(status);
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
        Estate.EstateType estateType = estate.typeFor(this);
        if (estateType.equals(Estate.EstateType.EMPTY)) {
            buyEstate(estate);
        } else if (estateType.equals(Estate.EstateType.OWNER)) {
            funds -= estate.getEmptyPrice();
            estate.upgrade();
        }
        endTurn();
    }

    protected void buyEstate(Estate estate) {
        funds -= estate.getEmptyPrice();
        estate.setOwner(this);
        estates.add(estate);
    }

    public static Player createPlayerWith_Fund_Map_command_state_in_game(GameMap map, int initialFund, Game game) {
        Player player = new Player(map, initialFund);
        player.enterGame(game);
        return player;
    }

    public void sayNo() {
        endTurn();
    }

    public void addPoint(int addedPoints) {
        points += addedPoints;
    }

    public HashMap<Tool, Integer> getTools() {
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
            tools.compute(toolById, (k, v) -> v + 1);
            points -= toolById.getPoints();
            if (toolHouse.canAffordWith(points) && tools.values().stream().reduce(0, (a, b) -> a + b) < 10) {
                waitForResponse();
                return;
            }
        }
        endTurn();
    }

    public int getPoints() {
        return points;
    }

    public static Player createPlayerWith_Fund_Map_Tools_command_state_in_game(GameMap map, int initialFund10, Game game, Tool... tools) {
        Player player = createPlayerWith_Fund_Map_command_state_in_game(map, initialFund10, game);
        if (tools != null && tools.length > 0)
            Arrays.stream(tools).forEach(tool -> player.getTools().compute(tool, (k, v) -> v + 1));
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

    public static Player createPlayerWith_Fund_Map_Lucky_command_state_in_game(GameMap map, int initialFund, Game game) {
        Player player = createPlayerWith_Fund_Map_command_state_in_game(map, initialFund, game);
        player.hasLuckyGod = true;
        return player;
    }

    public void goToHospital() {
        stuckIn(map.getHospital(), Hospital.HOSPITAL_DAYS);
    }

    public void stuckIn(Place place, int days) {
        currentPlace = place;
        stuckDays = days;
    }

    public int getStuckDays() {
        return stuckDays;
    }

    public String queryAsString(Report report) {
        return report.reportAsString(funds, points, estates, tools);
    }

    public boolean sellEstate(Estate estate) {
        if (!estates.contains(estate)) return false;
        funds += estate.getEmptyPrice() * (estate.getLevel().ordinal() + 1) * 2;
        estates.remove(estate);
        estate.setOwner(null);
        return true;
    }

    public void sellTool(Tool tool) {
        if (tools.get(tool) == 0) return;
        tools.compute(tool, (k, v) -> v - 1);
        points += tool.getPoints();
    }

    public String helpAsString(GameHelp gameHelp) {
        return gameHelp.getHelpAsString();
    }

    public boolean setTool(Tool toolType, int steps) {
        if (tools.get(toolType) == 0 || (steps < -10 || steps > 10))
            return false;
        return map.setTool(toolType, steps, currentPlace);
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public static Player createPlayerWith_Fund_Map_place_command_state_in_game(GameMap map, int iniitialFund, Game game, Place initialPlace) {
        Player player = new Player(map, iniitialFund);
        player.enterGame(game);
        player.currentPlace = initialPlace;
        return player;
    }


    public static Player createPlayerWith_Fund_Map_WAIT_TURN_STATE(GameMap map, int initialFund) {
        Player player = new Player(map, initialFund);
        player.status = Status.WAIT_FOR_TURN;
        return player;
    }

    public void inTurn() {
        status = Status.WAIT_FOR_COMMAND;
    }

    public void decreaseStuckDays() {
        stuckDays--;
    }

    public void quit() {
        game.end();
    }

    public void enterGame(Game game) {
        this.game = game;
    }

    public boolean isLose() {
        return status == Status.BANKRUPT;
    }

    public enum Status {WAIT_FOR_COMMAND, WAIT_FOR_TURN, BANKRUPT, WAIT_FOR_RESPONSE}
}
