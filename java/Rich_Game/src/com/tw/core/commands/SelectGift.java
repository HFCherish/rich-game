package com.tw.core.commands;

import com.tw.core.asest.AssistancePower;
import com.tw.core.giftHouse.Fund;
import com.tw.core.giftHouse.LuckyGod;
import com.tw.core.giftHouse.PointCard;
import com.tw.core.house.House;
import com.tw.core.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class SelectGift implements Responsive {
    @Override
    public Player.Status respond(Player player, Response response) {
        House giftHouse = (House) player.getCurrentPlace();
        AssistancePower gift = giftHouse.getItemByIndex(response.getNumber());
        if (gift != null) {
            if (gift instanceof PointCard) {
                player.addPoint(gift.getValue());
            } else if (gift instanceof Fund) {
                player.addFunds(gift.getValue());
            } else if (gift instanceof LuckyGod) {
                player.getLuckyGod();
            }
        }
        return player.endTurn();
    }
}
