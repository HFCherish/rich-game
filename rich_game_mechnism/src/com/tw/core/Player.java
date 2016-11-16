package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.places.Place;
import com.tw.core.responses.Response;
import com.tw.core.tools.LuckyGod;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Player {
    protected Status status;
    private int stuckDays;
    private Game game;
    private Place currentPlace;
    private Command lastCommand;
    private Asest asests;
    private int luckyDays;

    private Player() {
        status = Status.WAIT_FOR_TURN;
        asests = new Asest();
    }

    public Status getStatus() {
        return status;
    }

    public Status execute(Command command) {
        if(status.equals(Status.WAIT_FOR_COMMAND)) {
            lastCommand = command;
            status = command.execute(this);
        }
        return status;
    }

    public Status inTurn() {
        status = Status.WAIT_FOR_COMMAND;
        return status;
    }

    public Status respond(Response response) {
        if(status.equals(Status.WAIT_FOR_RESPONSE)) {
            status = lastCommand.respond(response, this);
        }
        return status;
    }

    public int getStuckDays() {
        return stuckDays;
    }

    public static Player createPlayerWithGame_Fund_CommandState(Game game, int initialFund) {
        Player player = new Player();
        player.game = game;
        player.asests.addFund(initialFund);
        player.status = Status.WAIT_FOR_COMMAND;
        return player;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public Game getGame() {
        return game;
    }

    public Command lastCommand() {
        return lastCommand;
    }

    public void setLastCommand(Command lastCommand) {
        this.lastCommand = lastCommand;
    }

    public Asest getAsests() {
        return asests;
    }

    public void moveTo(Place place) {
        currentPlace = place;
    }

    public void getLuckyGod() {
        luckyDays = LuckyGod.LUCKY_DAYS;
    }

    public boolean isLucky() {
        return luckyDays > 0;
    }

    public enum Status {WAIT_FOR_COMMAND, BANKRUPT, WAIT_FOR_RESPONSE, WAIT_FOR_TURN}
}
