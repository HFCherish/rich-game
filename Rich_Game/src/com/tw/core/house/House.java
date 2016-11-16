package com.tw.core.house;

import com.tw.core.asest.AssistancePower;
import com.tw.core.map.Place;
import com.tw.core.player.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/13/16.
 */
public abstract class House implements Place {
    protected List<AssistancePower> items;

    public House(AssistancePower... items) {
        this.items = Arrays.asList(items);
    }

    public AssistancePower getItemByIndex(int index_startFrom1) {
        if(index_startFrom1 > items.size() || index_startFrom1 < 1) return null;
        return items.get(index_startFrom1 - 1);
    }

    public abstract Player.Status comeHere(Player player);
}
