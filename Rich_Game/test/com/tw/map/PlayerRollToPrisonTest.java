package com.tw.map;

import com.tw.Dice;
import com.tw.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/13/16.
 */
public class PlayerRollToPrisonTest {
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
    public void should_player_get_into_prison_and_end_turn() {
        Prison prison = new Prison();
        when(map.move(anyObject(), anyInt())).thenReturn(prison);
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getStuckDays(), is(0));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStuckDays(), is(2));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }
}