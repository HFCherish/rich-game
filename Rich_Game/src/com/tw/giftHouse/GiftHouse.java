package com.tw.giftHouse;

import com.tw.asest.AssistancePower;
import com.tw.commands.ResponsiveFactory;
import com.tw.house.House;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/13/16.
 */
public class GiftHouse extends House {

    public GiftHouse(AssistancePower... gifts) {
        super(gifts);
    }

    @Override
    public Player.Status comeHere(Player player) {
        player.moveTo(this);
        player.setResponseCommand(ResponsiveFactory.SelectGift);
        return player.waitForResponse();
    }
}
