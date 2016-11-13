package com.tw.player;

import com.tw.Dice;
import com.tw.map.GameMap;
import com.tw.map.Place;
import com.tw.map.Block;
import com.tw.map.Bomb;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/13/16.
 */
public class PlayerRollToToolTest {
    public static final int INITIAL_FUND_10 = 10;
    private GameMap map;
    private Dice dice;
    private Player currentPlayer;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
    }

    @Test
    public void should_stop_and_end_turn_if_pass_block() {
        Place block = new Block();
        when(map.move(anyObject(), anyInt())).thenReturn(block);
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_get_into_hospital_and_end_turn_if_pass_bomb() {
        Place bomb = new Bomb();
        when(map.move(anyObject(), anyInt())).thenReturn(bomb);
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.hospitalDays(), is(0));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
        assertThat(currentPlayer.hospitalDays(), is(3));
    }
}
