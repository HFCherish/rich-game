package com.tw.core.places;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.GameMap;
import com.tw.core.Player;
import com.tw.core.commands.Command;
import com.tw.core.commands.CommandFactory;
import com.tw.core.commands.Roll;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/17/16.
 */
public class PlayerRollToMineralTest {
    public static final int INITIAL_FUND = 10000;
    public static final int MINERAL_POINTS = 50;
    private GameMap map;
    private Dice dice;
    private Mineral mineral;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        mineral = new Mineral(MINERAL_POINTS);
    }

    @Test
    public void should_get_points_and_end_turn() {
        when(map.move(anyObject(), anyInt())).thenReturn(mineral);
        Game game = mock(Game.class);
        when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(mineral));
        assertThat(player.getAsests().getPoints(), is(MINERAL_POINTS));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }
}
