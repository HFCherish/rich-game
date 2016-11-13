package com.tw.player;

import com.tw.Dice;
import com.tw.map.Estate;
import com.tw.map.GameMap;
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
public class PlayerRollToOthersEstate {
    public static final int INITIAL_FUND_10 = 10;
    public static final int EMPTY_ESTATE_PRICE_5 = 5;
    private GameMap map;
    private Dice dice;
    private Estate othersEstate;
    private Player currentPlayer;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
        othersEstate = new Estate(EMPTY_ESTATE_PRICE_5);
        othersEstate.sellTo(Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10));
        when(map.move(anyObject(), anyInt())).thenReturn(othersEstate);
    }

    @Test
    public void should_end_turn_and_not_charge_if_has_lucky_god() {
        currentPlayer = Player.createPlayerWith_Fund_Map_Lucky(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_charge_and_end_turn_if_no_lucky_god() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 - EMPTY_ESTATE_PRICE_5));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_bankrupt_if_funds_less_than_0_after_charge() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, EMPTY_ESTATE_PRICE_5-1);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(EMPTY_ESTATE_PRICE_5-1));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getFunds(), is(-1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.BANKRUPT));
    }
}
