package com.tw.core.giftHouse;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Fund extends Gift {
    private int value;

    public Fund(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
