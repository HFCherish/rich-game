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
public class PlayerRollToOtherEstateTest {
    public static final int INITIAL_FUND = 10000;
    public static final int EMPTY_PRICE = 200;
    private GameMap map;
    private Dice dice;
    private Estate otherEstate;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        otherEstate = new Estate(EMPTY_PRICE);
        when(map.move(anyObject(), anyInt())).thenReturn(otherEstate);
    }

    @Test
    public void should_charge_and_end_turn_if_has_enough_money() {
        Game game = mock(Game.class);
        when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        otherEstate.upgrade();
        otherEstate.setOwner(Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsests().getFunds(), is(INITIAL_FUND - EMPTY_PRICE));
        assertThat(player.getCurrentPlace(), is(otherEstate));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

    @Test
    public void should_bankrupt_if_not_has_enough_money() {
        Game game = mock(Game.class);
        when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        otherEstate.upgrade();
        otherEstate.setOwner(Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.BANKRUPT));
        assertThat(player.getCurrentPlace(), is(otherEstate));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

    @Test
    public void should_end_turn_if_has_lucky_god() {
        Game game = mock(Game.class);
        when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        player.getLuckyGod();
        otherEstate.setOwner(Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsests().getFunds(), is(0));
        assertThat(player.getCurrentPlace(), is(otherEstate));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

    @Test
    public void should_end_turn_if_has_the_owner_of_other_estate_is_in_prison_or_hospital() {
        Game game = mock(Game.class);
        when(game.getMap()).thenReturn(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        Player anotherPlayer = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        anotherPlayer.stuckFor(1);
        otherEstate.setOwner(anotherPlayer);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsests().getFunds(), is(0));
        assertThat(player.getCurrentPlace(), is(otherEstate));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }
}
