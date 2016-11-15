package com.tw.player;

import com.tw.Dice;
import com.tw.Game;
import com.tw.commands.CommandFactory;
import com.tw.map.*;
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
    Game game = mock((Game.class));

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
    }

    @Test
    public void should_stop_and_end_turn_if_pass_block() {
        Place block = new BlockPlace(new Prison());
        when(map.move(anyObject(), anyInt())).thenReturn(block);
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_get_into_hospital_and_end_turn_if_pass_bomb() {
        Place bomb = new BombPlace(new Prison());
        when(map.move(anyObject(), anyInt())).thenReturn(bomb);
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getStuckDays(), is(0));
        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(currentPlayer.getStuckDays(), is(3));
    }
}
