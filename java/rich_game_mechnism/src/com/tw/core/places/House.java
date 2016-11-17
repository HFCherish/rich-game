package com.tw.core.places;

import com.tw.core.Player;
import com.tw.core.assistentPower.AssistentPower;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/17/16.
 */
public abstract class House extends Place {
    protected final List<AssistentPower> items;

    public House(AssistentPower... items) {
        this.items = Arrays.asList(items);
    }

    public abstract Player.Status comeHere(Player player);
}
