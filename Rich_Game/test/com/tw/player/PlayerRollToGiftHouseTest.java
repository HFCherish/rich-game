package com.tw.player;

import com.tw.Dice;
import com.tw.asest.AssistancePower;
import com.tw.giftHouse.Fund;
import com.tw.giftHouse.PointCard;
import com.tw.giftHouse.GiftHouse;
import com.tw.giftHouse.LuckyGod;
import com.tw.house.House;
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
public class PlayerRollToGiftHouseTest {
    public static final int INITIAL_FUND_10 = 10;
    public static final int POINT_VALUE = 5;
    private GameMap map;
    private Dice dice;
    private Player currentPlayer;
    private House giftHouse;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
    }

    @Test
    public void should_wait_for_response_if_has_enough_point_to_buy_tool() {
        giftHouse = new GiftHouse();
        when(map.move(anyObject(), anyInt())).thenReturn(giftHouse);
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_addPoints_and_end_turn_if_select_point_card() {
        giftHouse = mock(House.class);
        AssistancePower gift1_point = new PointCard(POINT_VALUE);
        when(giftHouse.getItemByIndex(anyInt())).thenReturn(gift1_point);
        when(map.move(anyObject(), anyInt())).thenReturn(giftHouse);

        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getPoints(), is(0));
        currentPlayer.selectGift(1);
        assertThat(currentPlayer.getPoints(), is(POINT_VALUE));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_add_funds_and_end_turn_if_select_fund() {
        giftHouse = mock(House.class);
        AssistancePower gift_fund = new Fund(INITIAL_FUND_10);
        when(giftHouse.getItemByIndex(anyInt())).thenReturn(gift_fund);
        when(map.move(anyObject(), anyInt())).thenReturn(giftHouse);

        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        currentPlayer.selectGift(1);
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 + INITIAL_FUND_10));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_get_lucky_god_and_end_turn_if_select_lucky_god() {
        giftHouse = mock(House.class);
        AssistancePower gift_luckyGod = new LuckyGod();
        when(giftHouse.getItemByIndex(anyInt())).thenReturn(gift_luckyGod);
        when(map.move(anyObject(), anyInt())).thenReturn(giftHouse);

        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.isLucky(), is(false));
        currentPlayer.selectGift(1);
        assertThat(currentPlayer.isLucky(), is(true));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_if_select_wrong() {
        giftHouse = mock(House.class);
        when(giftHouse.getItemByIndex(anyInt())).thenReturn(null);
        when(map.move(anyObject(), anyInt())).thenReturn(giftHouse);

        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.roll(dice);

        currentPlayer.selectGift(4);
        assertThat(currentPlayer.isLucky(), is(false));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.getPoints(), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }
}
