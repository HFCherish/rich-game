package com.tw.giftHouse;

import com.tw.giftHouse.Gift;

/**
 * Created by pzzheng on 11/13/16.
 */
public class Fund implements Gift {
    private int value;

    public Fund(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
