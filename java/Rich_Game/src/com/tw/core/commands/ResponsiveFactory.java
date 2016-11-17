package com.tw.core.commands;

import com.tw.core.map.Estate;

/**
 * Created by pzzheng on 11/15/16.
 */
public class ResponsiveFactory {
    public static Responsive SelectGift = new SelectGift();
    public static Responsive BuyTool = new BuyTool();

    public static Responsive UpgradeEstate(Estate estate) {
        return new UpgradeEstate(estate);
    }

    public static Responsive BuyEstate(Estate emptyEstate) {
        return new BuyEstate(emptyEstate);
    }
}
