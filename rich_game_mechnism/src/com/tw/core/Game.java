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
    private Command currentCommand;

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
        return currentPlayer().getStatus().equals(Player.Status.WAIT_FOR_COMMAND) ? currentPlayer().execute(command) : currentPlayer().getStatus();
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player.Status respond(Response response) {
        return currentPlayer().getStatus().equals(Player.Status.WAIT_FOR_RESPONSE) ? currentPlayer().respond(response, currentCommand) : currentPlayer().getStatus();
    }

    public enum Status {END, START}
}
