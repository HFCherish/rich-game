package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.GameMap;
import com.tw.core.Player;
import com.tw.core.places.Estate;
import com.tw.core.tools.Tool;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/17/16.
 */
public class NonFinalCommandTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Dice dice;
    private Player player;
    private Game game;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        game = new Game(map);
    }

    @Test
    public void should_wait_for_next_command_if_no_block_when_use_block() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));

        player.execute(CommandFactory.UseBlock(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
    }

    @Test
    public void should_wait_for_next_command_and_use_block_if_has_block_when_use_block() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addTool(Tool.BLOCK);
        when(map.putBlock(anyObject(), anyInt())).thenReturn(true);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(true));

        player.execute(CommandFactory.UseBlock(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
    }

    @Test
    public void should_wait_for_next_command_if_no_bomb_when_use_bomb() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BOMB), is(false));

        player.execute(CommandFactory.UseBomb(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BOMB), is(false));
    }

    @Test
    public void should_wait_for_next_command_and_use_bomb_if_has_bomb_when_use_bomb() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addTool(Tool.BOMB);
        when(map.putBomb(anyObject(), anyInt())).thenReturn(true);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BOMB), is(true));

        player.execute(CommandFactory.UseBomb(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BOMB), is(false));
    }

    @Test
    public void should_wait_for_next_command_if_no_robot_when_use_robot() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.ROBOT_DULL), is(false));

        player.execute(CommandFactory.UseRobot);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.ROBOT_DULL), is(false));
    }

    @Test
    public void should_wait_for_next_command_and_use_robot_if_has_robot_when_use_robot() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addTool(Tool.ROBOT_DULL);
        when(map.useRobot(anyObject())).thenReturn(true);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.ROBOT_DULL), is(true));

        player.execute(CommandFactory.UseRobot);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.ROBOT_DULL), is(false));
    }

    @Test
    public void should_wait_for_next_command_if_not_has_that_estate_when_use_estate() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        int emptyPrice = 500;
        Estate estate = new Estate(emptyPrice);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasEstate(estate), is(false));

        player.execute(CommandFactory.SellEstate(estate));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasEstate(estate), is(false));
        assertThat(estate.getOwner(), is(nullValue()));
        assertThat(player.getAsests().getFunds(), is(0));
    }

    @Test
    public void should_wait_for_next_command_and_sell_estate_if_has_that_estate_when_use_estate() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        int emptyPrice = 500;
        Estate estate = new Estate(emptyPrice);
        estate.setOwner(player);
        estate.upgrade();
        player.getAsests().addEstate(estate);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasEstate(estate), is(true));
        assertThat(estate.getOwner(), is(player));

        player.execute(CommandFactory.SellEstate(estate));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasEstate(estate), is(false));
        assertThat(estate.getOwner(), is(nullValue()));
        assertThat(estate.getLevel(), is(Estate.Level.EMPTY));
        assertThat(player.getAsests().getFunds(), is(emptyPrice * 4));
    }

    @Test
    public void should_wait_for_next_command_if_not_has_that_tool_when_use_tool() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, 0);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));

        player.execute(CommandFactory.SellTool(Tool.BLOCK));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
        assertThat(player.getAsests().getFunds(), is(0));
    }

    @Test
    public void should_wait_for_next_command_and_sell_tool_if_has_that_tool_when_use_tool() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, 0);
        player.getAsests().addPoints(Tool.BLOCK.getValue());
        player.getAsests().addTool(Tool.BLOCK);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(true));

        player.execute(CommandFactory.SellTool(Tool.BLOCK));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
        assertThat(player.getAsests().getPoints(), is(Tool.BLOCK.getValue()));
    }
}
