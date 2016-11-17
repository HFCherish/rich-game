package com.tw.core.assistentPower;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Tool extends AssistentPower{
    public static Tool BLOCK = new Tool(50);
    public static Tool ROBOT_DULL = new Tool(30);
    public static Tool BOMB = new Tool(50);

    Tool(int value) {
        super(value);
    }
}
