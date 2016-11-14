package com.tw.map;

/**
 * Created by pzzheng on 11/12/16.
 */
public class Position {
    private final int x;
    private final int y;
    private Direction direction;

    public Position(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Position) {
            Position another = (Position) obj;
            return x== another.x && y== another.y && direction.equals(another.direction);
        }
        return false;
    }
}
