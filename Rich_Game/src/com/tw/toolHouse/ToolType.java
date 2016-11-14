package com.tw.toolHouse;

/**
 * Created by pzzheng on 11/14/16.
 */
public enum  ToolType implements Tool{
    Block {
        @Override
        public int getPoints() {
            return 50;
        }
    }, Bomb {
        @Override
        public int getPoints() {
            return 50;
        }
    }, RobotDull {
        @Override
        public int getPoints() {
            return 30;
        }
    }

}
