package com.tw.player;

import com.tw.GameHelp;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolType;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/13/16.
 */
public class PlayerCommandBeforeRollTest {
    public static final int INITIAL_FUND_10 = 10;
    GameMap map = mock(GameMap.class);
    private Player currentPlayer;

    @Test
    public void should_show_right_asset_report() {
        int EMPTY_PRICE_5 = 5;
        GameMap map = mock(GameMap.class);

        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10 * 2, ToolType.Bomb, ToolType.Block);
        Estate emptyEstate = new Estate(EMPTY_PRICE_5);
        currentPlayer.buyEstate(emptyEstate);
        Estate thatch = new Estate(EMPTY_PRICE_5);
        thatch.upgrade();
        currentPlayer.buyEstate(thatch);

        MatcherAssert.assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        String reportResult = currentPlayer.queryAsString(new DefaultReport());
        MatcherAssert.assertThat(reportResult, containsString("资金: " + INITIAL_FUND_10 + "元"));
        MatcherAssert.assertThat(reportResult, containsString("点数: 0点"));
        MatcherAssert.assertThat(reportResult, containsString("空地1处"));
        MatcherAssert.assertThat(reportResult, containsString("茅屋1处"));
        MatcherAssert.assertThat(reportResult, containsString("洋房0处"));
        MatcherAssert.assertThat(reportResult, containsString("摩天楼0处"));
        MatcherAssert.assertThat(reportResult, containsString("路障1个"));
        MatcherAssert.assertThat(reportResult, containsString("炸弹1个"));
        MatcherAssert.assertThat(reportResult, containsString("机器娃娃0个"));
        MatcherAssert.assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_able_to_sell_estate_when_waiting_for_command() {
        int empty_house_price_5 = 5;
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10 + empty_house_price_5);
        Estate emptyEstate = new Estate(empty_house_price_5);
        currentPlayer.buyEstate(emptyEstate);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.estates.size(), is(1));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.OWNER));

        assertThat(currentPlayer.sellEstate(emptyEstate), is(true));

        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 + empty_house_price_5 * 2));
    }

    @Test
    public void should_not_able_to_sell_estate_if_not_has_that_estate_when_waiting_for_command() {
        int empty_house_price_5 = 5;
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        Estate emptyEstate = new Estate(empty_house_price_5);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));

        assertThat(currentPlayer.sellEstate(emptyEstate), is(false));

        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
    }

    @Test
    public void should_able_to_sell_tool_when_waiting_for_command() {
        Tool tool = ToolType.Block;
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10, tool);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(1));

        currentPlayer.sellTool(tool);

        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints() > 0, is(true));
    }

    @Test
    public void should_not_sell_tool_if_not_has_that_tool_when_waiting_for_command() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));

        currentPlayer.sellTool(ToolType.Block);

        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
    }

    @Test
    public void should_able_to_get_help_when_waiting_for_command() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);
        GameHelp gameHelp = mock(GameHelp.class);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        currentPlayer.helpAsString(gameHelp);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

    }

    @Test
    public void should_not_set_blocker_if_does_not_have_blocker() {
        currentPlayer = Player.createPlayerWith_Fund_Map_COMMAND_STATE(map, INITIAL_FUND_10);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.Block, 1), is(false));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

    }

    @Test
    public void should_not_set_tool_if_steps_out_of_range() {
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10, ToolType.Block, ToolType.Bomb, ToolType.RobotDull);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.Block, 15), is(false));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.Bomb, 15), is(false));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.RobotDull, 15), is(false));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_able_to_set_tool_when_has_that_tool() {
        when(map.setTool(anyObject(), anyInt(), anyObject())).thenReturn(true);
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10, ToolType.Block, ToolType.Bomb, ToolType.RobotDull);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.Block, 10), is(true));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.Bomb, 10), is(true));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        assertThat(currentPlayer.setTool(ToolType.RobotDull, 10), is(true));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }
}
