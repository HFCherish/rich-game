package com.tw.player;

import com.tw.Dice;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/12/16.
 */
public class PlayerRollToEmptyEstateTest {
    public static final int INITIAL_FUND_10 = 10;
    public static final int EMPTY_PRICE_5 = 5;
    private GameMap map;
    private Dice dice;
    private Estate emptyEstate;
    private Player currentPlayer;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
        emptyEstate = new Estate(EMPTY_PRICE_5);
        when(map.move(anyObject(), anyInt())).thenReturn(emptyEstate);
    }

    @Test
    public void should_wait_for_response_if_has_enough_money_to_buy() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_buy_estate_if_say_yes() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        currentPlayer.roll(dice);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));

        currentPlayer.sayYes();
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 - emptyEstate.getEmptyPrice()));
        assertThat(currentPlayer.estates.size(), is(1));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.OWNER));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_if_say_no() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        currentPlayer.roll(dice);
        currentPlayer.sayNo();

        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));
    }

    @Test
    public void should_end_turn_if_no_enough_money() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, EMPTY_PRICE_5-1);

        currentPlayer.roll(dice);

        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
        assertThat(currentPlayer.getFunds(), is(EMPTY_PRICE_5-1));
    }
}
