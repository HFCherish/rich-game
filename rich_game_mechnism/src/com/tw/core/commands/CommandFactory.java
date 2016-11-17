package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.places.Estate;

/**
 * Created by pzzheng on 11/16/16.
 */
public class CommandFactory {
    public static Command BuyTool = new Roll.BuyTool();
    public static Command SelectGift = new Roll.SelectGift();

    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }

    public static Command BuyEstate(Estate estate) {
        return new Roll.BuyEstate(estate);
    }

    public static Command UpgradeEstate(Estate estate) {
        return new Roll.UpgradeEstate(estate);
    }

    public static Command Block(int steps) {
        return new UseBlock(steps);
    }
}
