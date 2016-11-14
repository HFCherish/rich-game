package com.tw.player;

import com.tw.Dice;
import com.tw.house.House;
import com.tw.map.GameMap;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolHouse;
import com.tw.toolHouse.ToolType;
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
    public static int POINT_BLOCK;
    public static int POINT_ROBOT;
    private GameMap map;
    private Dice dice;
    private House toolHouse;
    private Player currentPlayer;
    private Tool tool_block;
    private Tool tool_robot;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = () -> 1;
        tool_block = ToolType.Block;
        tool_robot = ToolType.RobotDull;
        POINT_BLOCK = tool_block.getPoints();
        POINT_ROBOT = tool_robot.getPoints();
        toolHouse = new ToolHouse(tool_block, tool_robot);

        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
    }

    @Test
    public void should_wait_for_response_if_has_enough_point_to_buy_tool() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_BLOCK + 1);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_end_turn_if_has_no_enough_point_before_buying() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        currentPlayer.roll(dice);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_get_tool_and_wait_for_response_after_buying_and_still_has_enough_points() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_BLOCK + POINT_ROBOT);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(0));
        assertThat(currentPlayer.getPoints(), is(POINT_BLOCK + POINT_ROBOT));
        currentPlayer.buyTool(1);
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(1));
        assertThat(currentPlayer.getTools().get(tool_block), is(1));
        assertThat(currentPlayer.getPoints(), is(POINT_ROBOT));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
    }

    @Test
    public void should_end_turn_after_buying_and_no_enough_points() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_ROBOT);
        currentPlayer.roll(dice);

        assertThat(currentPlayer.getPoints(), is(POINT_ROBOT));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(0));
        currentPlayer.buyTool(1);
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(1));
        assertThat(currentPlayer.getPoints(), is(POINT_ROBOT - POINT_BLOCK));
        assertThat(currentPlayer.getTools().get(tool_block), is(1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_after_buying_and_contains_10_tools() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_ROBOT * 10 + POINT_BLOCK);
        currentPlayer.roll(dice);

        for (int i = 0; i < 10; i++)
            currentPlayer.buyTool(2);
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(10));
        assertThat(currentPlayer.getPoints(), is(POINT_BLOCK));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_when_buying_and_input_quit() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        currentPlayer.addPoint(POINT_BLOCK + 1);

        currentPlayer.roll(dice);
        currentPlayer.buyTool(ToolHouse.QUIT_INDEX);
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_if_contains_10_tools_before_buying() {
        //contains 10 tools before get into tool house
        Tool[] tenTools = new Tool[10];
        for( int i=0; i<10; i++ )
            tenTools[i] = tool_block;
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10, tenTools);
        currentPlayer.addPoint(POINT_BLOCK);

        currentPlayer.roll(dice);
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a+b), is(10));
        assertThat(currentPlayer.getPoints(), is(POINT_BLOCK));
        assertThat(currentPlayer.getStatus(), is(Player.Status.END_TURN));
    }
}
