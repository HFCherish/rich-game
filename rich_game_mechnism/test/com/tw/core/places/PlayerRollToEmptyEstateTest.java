package com.tw.core.places;

import com.tw.core.*;
import com.tw.core.commands.CommandFactory;
import com.tw.core.places.Estate;
import com.tw.core.places.Place;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/16/16.
 */
public class PlayerRollToEmptyEstateTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Dice dice;
    private Place emptyEstate;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
    }

    @Test
    public void should_wait_for_response_if_has_enough_money() {
        emptyEstate = new Estate(200);
        when(map.move(anyObject(), anyInt())).thenReturn(emptyEstate);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }
}