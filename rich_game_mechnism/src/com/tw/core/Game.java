package com.tw.core;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Game {
    private List<Player> players;
    private Status status;

    public Game(GameMap map) {
        status = Status.START;
    }

    public void initialPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.get(0).inTurn();
    }

    public Status getStatus() {
        return status;
    }

    public void quit() {
        status = Status.END;
    }

    public enum Status {END, START}
}
