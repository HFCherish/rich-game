package com.tw.core;

import java.util.ArrayList;
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
        players = new ArrayList<>();
    }

    public void initialPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.get(0).inTurn();
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

    public enum Status {END, START}
}
