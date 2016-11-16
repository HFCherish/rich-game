package com.tw.core.tools;

/**
 * Created by pzzheng on 11/16/16.
 */
public enum Tool {
    BLOCK(50), ROBOT_DULL(30), BOMB(50);

    private final int value;

    Tool(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
