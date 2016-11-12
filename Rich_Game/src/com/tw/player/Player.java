package com.tw.player;

import com.tw.Dice;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import com.tw.map.Place;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Player {
    private final GameMap map;
    private Status status;
    private int funds;
    private Place currentPlace;

    public Player(GameMap map, int initialFund) {
        this.map = map;
        this.funds = initialFund;
        this.status = Status.WAIT_FOR_COMMAND;
    }

    public void roll(Dice dice) {
        currentPlace = map.move(currentPlace, dice.next());
        if( funds < ((Estate) currentPlace).getEmptyPrice() ) {
            status = Status.END_TURN;
            return;
        }
        status = Status.WAIT_FOR_RESPONSE;
    }

    public Status getStatus() {
        return status;
    }

    public int getFunds() {
        return funds;
    }

    public void sayYes() {
        Estate emptyEstate = (Estate) currentPlace;
        funds -= emptyEstate.getEmptyPrice();
        emptyEstate.sellTo(this);
        status = Status.END_TURN;
    }

    public static Player createPlayerWithFundAndMap(GameMap map, int initialFund) {
        return new Player(map, initialFund);
    }

    public void sayNo() {
        status = Status.END_TURN;
    }

    public enum Status {WAIT_FOR_COMMAND, END_TURN, WAIT_FOR_RESPONSE}
}
