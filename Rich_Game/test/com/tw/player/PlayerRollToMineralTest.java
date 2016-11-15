package com.tw.player;

import com.tw.Dice;
import com.tw.Game;
import com.tw.commands.CommandFactory;
import com.tw.map.GameMap;
import com.tw.map.MineralEstate;
import com.tw.map.Prison;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/15/16.
 */
public class PlayerRollToMineralTest {
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
    public void should_get_points_and_end_turn() {
        MineralEstate mineralEstate = new MineralEstate(10);
        when(map.move(anyObject(), anyInt())).thenReturn(mineralEstate);
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getPoints(), is(10));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

}
