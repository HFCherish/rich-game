package com.tw.core;

import com.tw.core.places.Estate;
import com.tw.core.places.Starting;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 11/17/16.
 */
public class GameMapTest {

    @Test
    public void should_able_to_move_from_a_place_to_another_place() {
        Starting starting = new Starting();
        Estate estate = new Estate(10);
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        GameMap map = new GameMap(starting, estate, estate1, estate2);

        assertThat(map.move(starting, 1), is(estate));
        assertThat(map.move(starting, 5), is(estate));

        assertThat(map.move(starting, -1), is(estate2));
        assertThat(map.move(starting, -5), is(estate2));

    }
}