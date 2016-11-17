package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.places.Estate;
import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/16/16.
 */
public class CommandFactory {
    public static Command BuyTool = new Roll.BuyTool();
    public static Command SelectGift = new Roll.SelectGift();
    public static Command UseRobot = new UseRobot();
    public static Command Query = new Query();

    public static Command Help = new Help();

    public static Command Roll(Dice dice) {
        return new Roll(dice);
    }

    public static Command BuyEstate(Estate estate) {
        return new Roll.BuyEstate(estate);
    }

    public static Command UpgradeEstate(Estate estate) {
        return new Roll.UpgradeEstate(estate);
    }

    public static Command UseBlock(int steps) {
        return new UseBlock(steps);
    }

    public static Command UseBomb(int steps) {
        return new UseBomb(steps);
    }


    public static Command SellEstate(Estate estate) {
        return new SellEstate(estate);
    }

    public static Command SellTool(Tool tool) {
        return new SellTool(tool);
    }
}
