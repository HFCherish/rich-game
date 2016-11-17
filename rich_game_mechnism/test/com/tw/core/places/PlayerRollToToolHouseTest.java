package com.tw.core.places;

import com.tw.core.*;
import com.tw.core.commands.Command;
import com.tw.core.commands.CommandFactory;
import com.tw.core.commands.Roll;
import com.tw.core.responses.Response;
import com.tw.core.assistentPower.Tool;
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
public class PlayerRollToToolHouseTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Dice dice;
    private ToolHouse toolHouse;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        toolHouse = new ToolHouse(Tool.BLOCK, Tool.BOMB, Tool.ROBOT_DULL);
    }

    @Test
    public void should_wait_for_response_if_has_enough_points() {
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addPoints(Tool.BLOCK.getValue());

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.getCurrentPlace(), is(toolHouse));
        assertThat(player.lastCommand() instanceof Roll.BuyTool, is(true));
    }

    @Test
    public void should_end_turn_if_not_has_enough_points() {
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        Command roll = CommandFactory.Roll(dice);
        player.execute(roll);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(toolHouse));
        assertThat(player.lastCommand() instanceof Roll, is(true));
    }

    @Test
    public void should_buy_the_tool_after_response() {
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addPoints(Tool.BLOCK.getValue() * 2);
        Command roll = CommandFactory.Roll(dice);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        player.execute(roll);

        player.respond(Response.GetAssistencePower(Tool.BLOCK));


        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.getCurrentPlace(), is(toolHouse));
        assertThat(player.getAsests().getPoints(), is(Tool.BLOCK.getValue()));
        assertThat(player.getAsests().getToolCount(Tool.BLOCK) > 0, is(true));
        assertThat(player.lastCommand() instanceof Roll.BuyTool, is(true));
    }

    @Test
    public void should_end_turn_if_no_enough_points_after_buying() {
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addPoints(Tool.BLOCK.getValue());
        Command roll = CommandFactory.Roll(dice);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        player.execute(roll);

        player.respond(Response.GetAssistencePower(Tool.BLOCK));


        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(toolHouse));
        assertThat(player.getAsests().getPoints(), is(0));
        assertThat(player.getAsests().getToolCount(Tool.BLOCK) > 0, is(true));
        assertThat(player.lastCommand() instanceof Roll.BuyTool, is(true));
    }

    @Test
    public void should_end_turn_if_quit_manualy_in_response() {
        when(map.move(anyObject(), anyInt())).thenReturn(toolHouse);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addPoints(Tool.BLOCK.getValue());
        Command roll = CommandFactory.Roll(dice);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        player.execute(roll);

        player.respond(Response.Quit);


        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getCurrentPlace(), is(toolHouse));
        assertThat(player.getAsests().getPoints(), is(Tool.BLOCK.getValue()));
        assertThat(player.getAsests().getToolCount(Tool.BLOCK) > 0, is(false));
        assertThat(player.lastCommand() instanceof Roll.BuyTool, is(true));
    }


}
