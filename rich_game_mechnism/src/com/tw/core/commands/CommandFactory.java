package com.tw.core.commands;

import com.tw.core.Dice;

/**
 * Created by pzzheng on 11/16/16.
 */
public class CommandFactory {
    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }
}
