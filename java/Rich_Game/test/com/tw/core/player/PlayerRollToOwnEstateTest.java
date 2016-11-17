package com.tw.core.player;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.commands.CommandFactory;
import com.tw.core.commands.Response;
import com.tw.core.commands.ResponsiveFactory;
import com.tw.core.map.Estate;
import com.tw.core.map.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/12/16.
 */
public class PlayerRollToOwnEstateTest {
    public static final int INITIAL_FUND_10 = 10;
    public static final int EMPTY_ESTATE_PRICE_5 = 5;
    private GameMap map;
    private Dice dice;
    private Estate ownEstate;
    private Player currentPlayer;
    Game game = mock((Game.class));

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
        ownEstate = new Estate(EMPTY_ESTATE_PRICE_5, 0);
    }

    @Test
    public void should_wait_for_response_if_has_enough_money_to_upgrade() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);
        ResponsiveFactory.BuyEstate(ownEstate).respond(currentPlayer, Response.Yes);
        currentPlayer.inTurn();
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(currentPlayer.getResponseCommand(), is(ResponsiveFactory.UpgradeEstate(ownEstate)));
    }

    @Test
    public void should_end_turn_if_no_enough_money_to_upgrade() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, EMPTY_ESTATE_PRICE_5-1, game);
        ResponsiveFactory.BuyEstate(ownEstate).respond(currentPlayer, Response.Yes);
        currentPlayer.inTurn();
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_upgrade_own_estate_if_say_yes_to_upgrade() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10 + EMPTY_ESTATE_PRICE_5, game);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);
        ResponsiveFactory.BuyEstate(ownEstate).respond(currentPlayer, Response.Yes);

        CommandFactory.Roll(dice).execute(currentPlayer);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(ownEstate.getLevel(), is(Estate.EstateLevel.EMPTY));

        currentPlayer.getResponseCommand().respond(currentPlayer, Response.Yes);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 - EMPTY_ESTATE_PRICE_5));
        assertThat(ownEstate.getLevel(), is(Estate.EstateLevel.THATCH));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_end_turn_if_say_no_to_upgrade() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10 + EMPTY_ESTATE_PRICE_5, game);
        ResponsiveFactory.BuyEstate(ownEstate).respond(currentPlayer, Response.Yes);
        when(map.move(anyObject(), anyInt())).thenReturn(ownEstate);

        CommandFactory.Roll(dice).execute(currentPlayer);

        currentPlayer.getResponseCommand().respond(currentPlayer, Response.No);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(ownEstate.getLevel(), is(Estate.EstateLevel.EMPTY));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }
}
