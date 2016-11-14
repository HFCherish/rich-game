package com.tw.giftHouse;

import com.tw.asest.AssistancePower;
import com.tw.house.House;
import com.tw.map.Position;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/13/16.
 */
public class GiftHouse implements House {

    public AssistancePower getItemByIndex(int giftIndex_startFrom1) {
        return null;
    }

    @Override
    public void comeHere(Player player) {
        player.setCurrentPlace(this);
        player.waitForResponse();
    }
}
