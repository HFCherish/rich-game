package com.tw.core.tools;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Gift extends AssistentPower{
    public static final Gift FUNDS = new Gift(2000);
    public static final Gift POINTS = new Gift(200);
    public static final Gift LUCKY_GOD = new Gift(5);

    Gift(int value) {
        super(value);
    }
}
