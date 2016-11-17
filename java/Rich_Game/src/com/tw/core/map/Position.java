package com.tw.core.map;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Position {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Position) {
            Position another = (Position) obj;
            return x== another.x && y== another.y;
        }
        return false;
    }
}
