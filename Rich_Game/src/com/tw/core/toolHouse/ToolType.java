package com.tw.core.toolHouse;

/**
 * Created by pzzheng on 11/14/16.
 */
public enum  ToolType implements Tool{
    Block {
        @Override
        public int getValue() {
            return 50;
        }
    }, Bomb {
        @Override
        public int getValue() {
            return 50;
        }
    }, RobotDull {
        @Override
        public int getValue() {
            return 30;
        }
    }

}
