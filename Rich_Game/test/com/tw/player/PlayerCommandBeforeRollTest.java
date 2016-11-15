package com.tw.player;

import com.tw.Game;
import com.tw.GameHelp;
import com.tw.commands.CommandFactory;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import com.tw.toolHouse.Tool;
import com.tw.toolHouse.ToolType;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mock;

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
    @Mock
    Game game;

    @Test
    public void should_show_right_asset_report() {
        int EMPTY_PRICE_5 = 5;
        GameMap map = mock(GameMap.class);

        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_command_state_in_game(map, INITIAL_FUND_10 * 2, game, ToolType.Bomb, ToolType.Block);
        Estate emptyEstate = new Estate(EMPTY_PRICE_5);
        currentPlayer.buyEstate(emptyEstate);
        Estate thatch = new Estate(EMPTY_PRICE_5);
        thatch.upgrade();
        currentPlayer.buyEstate(thatch);

        MatcherAssert.assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(CommandFactory.Query.execute(currentPlayer), is(Player.Status.WAIT_FOR_COMMAND));
//        MatcherAssert.assertThat(reportResult, containsString("资金: " + INITIAL_FUND_10 + "元"));
//        MatcherAssert.assertThat(reportResult, containsString("点数: 0点"));
//        MatcherAssert.assertThat(reportResult, containsString("空地1处"));
//        MatcherAssert.assertThat(reportResult, containsString("茅屋1处"));
//        MatcherAssert.assertThat(reportResult, containsString("洋房0处"));
//        MatcherAssert.assertThat(reportResult, containsString("摩天楼0处"));
//        MatcherAssert.assertThat(reportResult, containsString("路障1个"));
//        MatcherAssert.assertThat(reportResult, containsString("炸弹1个"));
//        MatcherAssert.assertThat(reportResult, containsString("机器娃娃0个"));
        MatcherAssert.assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_able_to_sell_estate_when_waiting_for_command() {
        int empty_house_price_5 = 5;
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10 + empty_house_price_5, game);
        Estate emptyEstate = new Estate(empty_house_price_5);
        currentPlayer.buyEstate(emptyEstate);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.estates.size(), is(1));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.OWNER));

        CommandFactory.SellEstate(emptyEstate).execute(currentPlayer);

        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10 + empty_house_price_5 * 2));
    }

    @Test
    public void should_not_able_to_sell_estate_if_not_has_that_estate_when_waiting_for_command() {
        int empty_house_price_5 = 5;
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);
        Estate emptyEstate = new Estate(empty_house_price_5);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));

        CommandFactory.SellEstate(emptyEstate).execute(currentPlayer);

        assertThat(emptyEstate.typeFor(currentPlayer), is(Estate.EstateType.EMPTY));
        assertThat(currentPlayer.estates.size(), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getFunds(), is(INITIAL_FUND_10));
    }

    @Test
    public void should_able_to_sell_tool_when_waiting_for_command() {
        Tool tool = ToolType.Block;
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_command_state_in_game(map, INITIAL_FUND_10, game, tool);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(1));

        CommandFactory.SellTool(tool).execute(currentPlayer);

        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints() > 0, is(true));
    }

    @Test
    public void should_not_sell_tool_if_not_has_that_tool_when_waiting_for_command() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));

        CommandFactory.SellTool(ToolType.Block).execute(currentPlayer);

        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a, b) -> a + b), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getPoints(), is(0));
    }

    @Test
    public void should_able_to_get_help_when_waiting_for_command() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);
        GameHelp gameHelp = mock(GameHelp.class);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        CommandFactory.Help.execute(currentPlayer);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

    }

    @Test
    public void should_not_set_blocker_if_does_not_have_blocker() {
        currentPlayer = Player.createPlayerWith_Fund_Map_command_state_in_game(map, INITIAL_FUND_10, game);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a,b) -> a+b), is(0));

        CommandFactory.Block(1).execute(currentPlayer);
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a,b) -> a+b), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_not_set_tool_if_steps_out_of_range() {
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_command_state_in_game(map, INITIAL_FUND_10, game, ToolType.Block, ToolType.Bomb, ToolType.RobotDull);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a,b) -> a+b), is(3));

        CommandFactory.Block(15).execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.Block), is(1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        CommandFactory.Bomb(15).execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.Bomb), is(1));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        CommandFactory.RobotDull.execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.RobotDull), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_able_to_set_tool_when_has_that_tool() {
        when(map.setTool(anyObject(), anyInt(), anyObject())).thenReturn(true);
        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_command_state_in_game(map, INITIAL_FUND_10, game, ToolType.Block, ToolType.Bomb, ToolType.RobotDull);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(currentPlayer.getTools().values().stream().reduce(0, (a,b) -> a+b), is(3));

        CommandFactory.Block(10).execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.Block), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        CommandFactory.Bomb(10).execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.Bomb), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        CommandFactory.RobotDull.execute(currentPlayer);
        assertThat(currentPlayer.getTools().get(ToolType.RobotDull), is(0));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }
}
