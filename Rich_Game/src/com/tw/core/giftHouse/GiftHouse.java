package com.tw.core.giftHouse;

import com.tw.core.asest.AssistancePower;
import com.tw.core.commands.ResponsiveFactory;
import com.tw.core.house.House;
import com.tw.core.player.Player;

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
