package com.tw.toolHouse;

import com.tw.map.Place;
import com.tw.player.Player;

/**
 * Created by pzzheng on 11/14/16.
 */
public enum  ToolType implements Place, Tool{
    Block {
        @Override
        public void comeHere(Player player) {
            player.endTurn();
        }

        @Override
        public int getPoints() {
            return 50;
        }
    }, Bomb {
        @Override
        public void comeHere(Player player) {
            player.endTurn();
            player.stuckFor(3);
        }

        @Override
        public int getPoints() {
            return 50;
        }
    }, RobotDull {
        @Override
        public void comeHere(Player player) {

        }

        @Override
        public int getPoints() {
            return 30;
        }
    };

    @Override
    public void comeHere(Player player) {

    }
}
