package com.tw.commands;

import com.tw.asest.AssistancePower;
import com.tw.giftHouse.Fund;
import com.tw.giftHouse.LuckyGod;
import com.tw.giftHouse.PointCard;
import com.tw.house.House;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/15/16.
 */
public class SelectGift implements Responsive {
    @Override
    public Player.Status respond(Player player, ResponseType responseType) {
        House giftHouse = (House) player.getCurrentPlace();
        AssistancePower gift = giftHouse.getItemByIndex(responseType.getNumber());
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
