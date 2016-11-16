package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.places.Place;
import com.tw.core.responses.Response;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Player {
    protected Status status;
    private int stuckDays;
    private int initialFund;
    private Game game;
    private Place currentPlace;

    private Player() {
        status = Status.WAIT_FOR_TURN;
    }

    public Status getStatus() {
        return status;
    }

    public Status execute(Command command) {
        if(status.equals(Status.WAIT_FOR_COMMAND)) {
            status = command.execute(this);
        }
        return status;
    }

    public Status inTurn() {
        status = Status.WAIT_FOR_COMMAND;
        return status;
    }

    public Status respond(Response response, Command lastCommand) {
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
        player.initialFund = initialFund;
        player.status = Status.WAIT_FOR_COMMAND;
        return player;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public Game getGame() {
        return game;
    }

    public enum Status {WAIT_FOR_COMMAND, BANKRUPT, WAIT_FOR_RESPONSE, WAIT_FOR_TURN}
}
