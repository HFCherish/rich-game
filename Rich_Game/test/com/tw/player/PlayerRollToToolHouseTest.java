package com.tw.player;

import com.tw.Dice;
import com.tw.asest.AssistancePower;
import com.tw.house.House;
import com.tw.map.GameMap;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolHouse;
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
public class PlayerRollToToolHouseTest {
    public static final int INITIAL_FUND_10 = 10;
    public static final int POINT_5_TOOL_1 = 5;
    public static final int POINT_6_TOOL_2 = 6;
    private GameMap map;
    private Dice dice;
    private House toolHouse;
    private Player currentPlayer;
    private Tool tool_5;
    private Tool tool_6;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
        tool_5 = mock(Tool.class);
        tool_6 = mock(Tool.class);
        when(tool_5.getPoints()).thenReturn(POINT_5_TOOL_1);
        when(tool_6.getPoints()).thenReturn(POINT_6_TOOL_2);
        toolHouse = new ToolHouse(tool_5, tool_6);
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
    }

    @Test
    public void should_wait_for_response_if_has_enough_point_to_buy_tool() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_5_TOOL_1 + 1);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_end_turn_if_has_no_enough_point_before_buying() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_5_TOOL_1 - 1);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_get_tool_and_wait_for_response_after_buying_and_still_has_enough_points() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_5_TOOL_1 + POINT_6_TOOL_2);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getTools().size(), is(0));
        assertThat(currentPlayer.getPoints(), is(POINT_5_TOOL_1 + POINT_6_TOOL_2));
        currentPlayer.buyTool(1);
        assertThat(currentPlayer.getTools().size(), is(1));
        assertThat(currentPlayer.getTools().contains(tool_5), is(true));
        assertThat(currentPlayer.getPoints(), is(POINT_6_TOOL_2));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_end_turn_after_buying_and_no_enough_points() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_6_TOOL_2);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getPoints(), is(POINT_6_TOOL_2));
        assertThat(currentPlayer.getTools().size(), is(0));
        currentPlayer.buyTool(1);
        assertThat(currentPlayer.getTools().size(), is(1));
        assertThat(currentPlayer.getPoints(), is(POINT_6_TOOL_2 - POINT_5_TOOL_1));
        assertThat(currentPlayer.getTools().contains(tool_5), is(true));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_after_buying_and_contains_10_tools() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_6_TOOL_2 * 10 + POINT_5_TOOL_1);
        currentPlayer.roll(dice);

        for (int i = 0; i < 10; i++)
            currentPlayer.buyTool(2);
        assertThat(currentPlayer.getTools().size(), is(10));
        assertThat(currentPlayer.getPoints(), is(POINT_5_TOOL_1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_when_buying_and_input_quit() {
        currentPlayer = Player.createPlayerWith_Fund_Map(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_5_TOOL_1 + 1);

        currentPlayer.roll(dice);
        currentPlayer.buyTool(ToolHouse.QUIT_INDEX);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_if_contains_10_tools_before_buying() {
        //contains 10 tools before get into tool house
        AssistancePower[] tenTools = new AssistancePower[10];
        for( int i=0; i<10; i++ )
            tenTools[i] = tool_5;
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools(map, INITIAL_FUND_10, tenTools);
        currentPlayer.addPoint(POINT_5_TOOL_1);

        currentPlayer.roll(dice);
        assertThat(currentPlayer.getTools().size(), is(10));
        assertThat(currentPlayer.getPoints(), is(POINT_5_TOOL_1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }
}
