package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.responses.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Game {
    private List<Player> players;
    private Status status;
    private int currentPlayerIndex;
    protected Command lastCommand;

    public Game(GameMap map) {
        status = Status.START;
        players = new ArrayList<>();
    }

    public void initialPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.get(0).inTurn();
        currentPlayerIndex = 0;
    }

    public Status getStatus() {
        if(players.stream().filter(player -> !player.getStatus().equals(Player.Status.BANKRUPT)).count() == 1) {
            status = Status.END;
        }
        return status;
    }

    public void quit() {
        status = Status.END;
    }

    public Player.Status execute(Command command) {
        Player.Status playerStatus = currentPlayer().getStatus();
        if( playerStatus.equals(Player.Status.WAIT_FOR_COMMAND)) {
            lastCommand = command;
            return currentPlayer().execute(command);
        }
        return playerStatus;
    }

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player.Status respond(Response response) {
        return currentPlayer().getStatus().equals(Player.Status.WAIT_FOR_RESPONSE) ? currentPlayer().respond(response, lastCommand) : currentPlayer().getStatus();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public enum Status {END, START}
}
