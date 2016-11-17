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
public class PlayerPassBombTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Dice dice;
    private Hospital hosipital;
    private BombPlace bombPlace;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        hosipital = new Hospital();
        bombPlace = new BombPlace(hosipital);
    }

    @Test
    public void should_end_turn_and_go_to_hospital() {
        when(map.move(anyObject(), anyInt())).thenReturn(bombPlace);
        when(map.getHospital()).thenReturn(hosipital);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(hosipital));
        assertThat(player.isStucked(), is(true));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

}
