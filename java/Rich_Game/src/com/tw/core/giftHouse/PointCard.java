package com.tw.core.giftHouse;

/**
 * Created by pzzheng on 11/13/16.
 */
public class PointCard extends Gift {
    private int value;

    public PointCard(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
