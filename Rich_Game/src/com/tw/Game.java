package com.tw;

import com.tw.map.GameMap;
import com.tw.player.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/14/16.
 */
public class Game {

    private GameMap map;
    private List<Player> players;
    private int currentPlayerIndex;
    private Status status;

    public Game(GameMap map) {
        this.map = map;
    }

    public void initialPlayers(final Player... players) {
        this.players = Arrays.asList(players);
        this.players.stream().forEach(player -> player.enterGame(this));
        setCurrentPlayer(0);
        status = Status.GAME_START;
    }

    private void setCurrentPlayer(int index) {
        this.currentPlayerIndex = index;
        players.get(currentPlayerIndex).inTurn();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if(players.get(currentPlayerIndex).getStuckDays() > 0) {
            players.get(currentPlayerIndex).decreaseStuckDays();
            nextPlayer();
        }
        players.get(currentPlayerIndex).inTurn();
    }

    public Game.Status getStatus() {
        return status;
    }

    public void end() {
        status = Status.GAME_END;
//        System.exit(1);
    }

    public void inform(Player.Status playerStatus) {
        if(playerStatus.equals(Player.Status.WAIT_FOR_TURN)) {
            nextPlayer();
        }
        else if(playerStatus.equals(Player.Status.BANKRUPT)) {
            if(players.stream().filter(player -> !player.isLose()).count() == 1) {
                end();
            }
        }
    }

    public enum Status {GAME_START, GAME_END}
}
