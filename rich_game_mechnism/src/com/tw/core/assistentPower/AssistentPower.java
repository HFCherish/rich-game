package com.tw.core.assistentPower;

/**
 * Created by pzzheng on 11/17/16.
 */
public abstract class AssistentPower {
    protected final int value;

    AssistentPower(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
