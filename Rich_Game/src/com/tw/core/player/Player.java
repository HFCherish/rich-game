package com.tw.core.player;

import com.tw.core.Game;
import com.tw.core.commands.Responsive;
import com.tw.core.giftHouse.LuckyGod;
import com.tw.core.map.Estate;
import com.tw.core.map.GameMap;
import com.tw.core.map.Place;
import com.tw.core.toolHouse.Tool;
import com.tw.core.toolHouse.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Player {
    private GameMap map;
    private String id;
    private String name;
    private Status status;
    private int funds;
    private Place currentPlace;
    private int points;
    HashMap<Tool, Integer> tools;
    List<Estate> estates;
    private int luckyDays;
    private int stuckDays;
    private Game game;
    private Responsive responseCommand;

    private Player() {
        this.status = Status.WAIT_FOR_COMMAND;
        points = 0;
        stuckDays = 0;
        luckyDays = 0;
        tools = new HashMap<>();
        Arrays.stream(ToolType.values()).forEach(toolType -> tools.compute(toolType, (k, v) -> 0));
        estates = new ArrayList<>();
    }

    public Player(GameMap map, int initialFund) {
        this();
        this.map = map;
        this.map.addPlayer(this);
        funds = initialFund;
    }

    public Player(String id, String name, int initialFund, GameMap map, Game game, Place starting) {
        this();
        this.id = id;
        this.name = name;
        this.funds = initialFund;
        this.map = map;
        this.game = game;
        currentPlace = starting;
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
        if(luckyDays > 0) {
            luckyDays--;
        }
        game.inform(status);
        return status;
    }

    public Status bankrupt() {
        status = Status.BANKRUPT;
        game.inform(status);
        return status;
    }

    public Status getStatus() {
        return status;
    }

    public int getFunds() {
        return funds;
    }

    public void decreaseFunds(int cost) {
        funds -= cost;
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

    public void getLuckyGod() {
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public enum Status {WAIT_FOR_COMMAND, WAIT_FOR_TURN, BANKRUPT, WAIT_FOR_RESPONSE}
}
