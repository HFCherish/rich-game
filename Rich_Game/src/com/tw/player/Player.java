package com.tw.player;

import com.tw.Game;
import com.tw.commands.ResponseType;
import com.tw.commands.Responsive;
import com.tw.commands.ResponsiveFactory;
import com.tw.giftHouse.LuckyGod;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import com.tw.map.Place;
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
    private int luckyDays;
    private int stuckDays;
    private Game game;
    private Responsive responseCommand;

    public Player(GameMap map, int initialFund) {
        this.map = map;
        this.map.addPlayer(this);
        funds = initialFund;
        this.status = Status.WAIT_FOR_COMMAND;
        points = 0;
        stuckDays = 0;
        luckyDays = 0;
        tools = new HashMap<>();
        Arrays.stream(ToolType.values()).forEach(toolType -> tools.compute(toolType, (k, v) -> 0));
        estates = new ArrayList<>();
        hasLuckyGod = false;
    }

    public void setResponseCommand(Responsive responseCommand) {
        this.responseCommand = responseCommand;
    }

    public Responsive getResponseCommand() {
        return responseCommand;
    }

    public void moveTo(Place place) {
        currentPlace = place;
    }

    public Status waitForResponse() {
        status = Status.WAIT_FOR_RESPONSE;
        return status;
    }

    public Status endTurn() {
        status = Status.WAIT_FOR_TURN;
        game.inform(status);
        return status;
    }

    public Status bankrupt() {
        status = Status.BANKRUPT;
        game.inform(status);
        return status;
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

    public int getPoints() {
        return points;
    }

    public static Player createPlayerWith_Fund_Map_Tools_command_state_in_game(GameMap map, int initialFund10, Game game, Tool... tools) {
        Player player = createPlayerWith_Fund_Map_command_state_in_game(map, initialFund10, game);
        if (tools != null && tools.length > 0)
            Arrays.stream(tools).forEach(tool -> player.getTools().compute(tool, (k, v) -> v + 1));
        return player;
    }

    public  void getLuckyGod() {
        luckyDays = LuckyGod.LuckyDays;
    }

    public boolean isLucky() {
        return luckyDays > 0;
    }

    public static Player createPlayerWith_Fund_Map_Lucky_command_state_in_game(GameMap map, int initialFund, Game game) {
        Player player = createPlayerWith_Fund_Map_command_state_in_game(map, initialFund, game);
        player.getLuckyGod();
        return player;
    }

    public void stuckFor(int days) {
        stuckDays = days;
    }

    public int getStuckDays() {
        return stuckDays;
    }

    public List<Estate> getEstates() {
        return estates;
    }

    public void addFunds(int income) {
        funds += income;
    }

    public GameMap getMap() {
        return map;
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

    public void enterGame(Game game) {
        this.game = game;
    }

    public boolean isLose() {
        return status == Status.BANKRUPT;
    }

    public Game getGame() {
        return game;
    }

    public void decreasePoints(int value) {
        points -= value;
    }

    public enum Status {WAIT_FOR_COMMAND, WAIT_FOR_TURN, BANKRUPT, WAIT_FOR_RESPONSE}
}
