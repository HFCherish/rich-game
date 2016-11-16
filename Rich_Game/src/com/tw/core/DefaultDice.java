package com.tw.core;

import java.util.Random;

/**
 * Created by pzzheng on 11/15/16.
 */
public class DefaultDice implements Dice {
    public static Dice dice = new DefaultDice();
    @Override
    public int next() {
        Random r =new Random();
        return r.nextInt(6) + 1;
    }
}
