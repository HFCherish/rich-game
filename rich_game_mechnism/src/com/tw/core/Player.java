package com.tw.core;

import com.tw.core.commands.Command;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Player {
    private Status status;

    public Player() {
        status = Status.WAIT_FOR_TURN;
    }

    public Status getStatus() {
        return status;
    }

    public Status execute(Command command) {
        if(!status.equals(Status.WAIT_FOR_COMMAND)) return status;
        return command.execute(this);
    }

    public Status inTurn() {
        status = Status.WAIT_FOR_COMMAND;
        return status;
    }

    public enum Status {WAIT_FOR_COMMAND, BANKRUPT, WAIT_FOR_RESPONSE, WAIT_FOR_TURN}
}
