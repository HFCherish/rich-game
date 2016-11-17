package com.tw.core.places;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.GameMap;
import com.tw.core.Player;
import com.tw.core.commands.Command;
import com.tw.core.commands.CommandFactory;
import com.tw.core.commands.Roll;
import com.tw.core.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/16/16.
 */
public class PlayerRollToOwnEstateTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Dice dice;
    private Estate ownEstate;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
    }

    @Test
    public void should_wait_for_response_if_has_enough_money() {
        ownEstate = new Estate(200);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);
        Game game = mock(Game.class);         when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        ownEstate.setOwner(player);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.getCurrentPlace(), is(ownEstate));
        assertThat(player.lastCommand() instanceof Roll.UpgradeEstate, is(true));
    }

    @Test
    public void should_end_turn_if_not_has_enough_money() {
        ownEstate = new Estate(200);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);
        Game game = mock(Game.class);         when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        ownEstate.setOwner(player);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(ownEstate));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

    @Test
    public void should_upgrade_estate_if_say_yes() {
        ownEstate = new Estate(200);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);
        Game game = mock(Game.class);         when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addEstate(ownEstate);
        ownEstate.setOwner(player);
        Command roll = CommandFactory.Roll(dice);

        player.execute(roll);

        player.respond(Response.Yes);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsests().getFunds(), is(INITIAL_FUND - 200));
        assertThat(player.getAsests().getEstates().get(0).getLevel(), is(Estate.Level.THATCH));
        assertThat(ownEstate.estateType(player), is(Estate.Type.OWNER));
    }

    @Test
    public void should_not_upgrade_estate_if_say_no() {
        ownEstate = new Estate(200);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);
        Game game = mock(Game.class);         when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addEstate(ownEstate);
        ownEstate.setOwner(player);
        Command roll = CommandFactory.Roll(dice);

        player.execute(roll);

        player.respond(Response.No);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsests().getFunds(), is(INITIAL_FUND));
        assertThat(player.getAsests().getEstates().get(0).getLevel(), is(Estate.Level.EMPTY));
        assertThat(ownEstate.estateType(player), is(Estate.Type.OWNER));
    }
}
