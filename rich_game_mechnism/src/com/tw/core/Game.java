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
    private GameMap map;

    public Game(GameMap map) {
        this.map = map;
        status = Status.START;
        players = new ArrayList<>();
    }

    public void initialPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.stream().forEach(player -> player.moveTo(map.getStarting()));
        this.players.get(0).inTurn();
        currentPlayerIndex = 0;
        map.initPlayers(this.players);
    }

    public Status getStatus() {
        return status;
    }

    public void quit() {
        status = Status.END;
    }

    public Player.Status execute(Command command) {
        Player.Status playerStatus = currentPlayer().getStatus();
        if (playerStatus.equals(Player.Status.WAIT_FOR_COMMAND)) {
            lastCommand = command;
            return currentPlayer().execute(command);
        }
        return playerStatus;
    }

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player.Status respond(Response response) {
        return currentPlayer().getStatus().equals(Player.Status.WAIT_FOR_RESPONSE) ? currentPlayer().respond(response) : currentPlayer().getStatus();
    }

    public void nextPlayer() {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (currentPlayer().getStatus().equals(Player.Status.BANKRUPT) || currentPlayer().isStucked());
    }

    public GameMap getMap() {
        return map;
    }

    public void inform(Player.Status status) {
        if( status.equals(Player.Status.BANKRUPT) && players.stream().filter(player -> !player.getStatus().equals(Player.Status.BANKRUPT)).count() == 1 ) {
            this.status = Status.END;
        }
    }

    public enum Status {END, START}
}
