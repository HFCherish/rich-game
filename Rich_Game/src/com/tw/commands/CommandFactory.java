package com.tw.commands;

import com.tw.map.Estate;

/**
 * Created by pzzheng on 11/15/16.
 */
public class CommandFactory {
    public static Command Query = new Query();
    public static Command RobotDull = new RobotDull();

    public static Command Block(int steps) {
        return new Block(steps);
    }

    public static Command Bomb(int steps) {
        return new Bomb(steps);
    }

    public static Command SellEstate(Estate estate) {
        return new SellEstate(estate);
    }
}
