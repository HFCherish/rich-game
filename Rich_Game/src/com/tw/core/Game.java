package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.commands.Response;
import com.tw.core.map.GameMap;
import com.tw.core.map.GameMapFactory;
import com.tw.core.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public Game() {
        map = GameMapFactory.defaultMap();
    }

    public void initPlayers(int initialFund, List<Map<String, String>> playerInfos) {
        List<Player> players = new ArrayList<>();
        playerInfos.stream().forEach(info -> players.add(new Player(info.get("id"), info.get("name"), initialFund, map, this, map.getStarting())));
        initPlayers(players.toArray(new Player[players.size()]));
    }

    public void initPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.stream().forEach(player -> player.enterGame(this));
        setCurrentPlayer(0);
        this.players.get(0).inTurn();
        status = Status.GAME_START;
    }

    public Player.Status execute(Command command) {
        return command.execute(players.get(currentPlayerIndex));
    }

    public Player.Status respond(Response response) {
        Player player = players.get(currentPlayerIndex);
        return player.getResponseCommand().respond(player, response);
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

    public void endGame() {
        status = Status.GAME_END;
//        System.exit(1);
    }

    public void inform(Player.Status playerStatus) {
        if(playerStatus.equals(Player.Status.WAIT_FOR_TURN)) {
            nextPlayer();
        }
        else if(playerStatus.equals(Player.Status.BANKRUPT)) {
            if(players.stream().filter(player -> !player.isLose()).count() == 1) {
                endGame();
            }
        }
    }

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public GameMap getMap() {
        return map;
    }

    public enum Status {GAME_START, GAME_END}
}
