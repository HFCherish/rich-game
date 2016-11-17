package com.tw.core.commands;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.GameMap;
import com.tw.core.Player;
import com.tw.core.places.Mineral;
import com.tw.core.tools.Tool;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

        player.execute(CommandFactory.Block(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
    }

    @Test
    public void should_wait_for_next_command_and_use_block_if_has_block_when_use_block() {
        player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);
        player.getAsests().addTool(Tool.BLOCK);
        when(map.putBlock(anyObject())).thenReturn(true);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(true));

        player.execute(CommandFactory.Block(1));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player.getAsests().hasTool(Tool.BLOCK), is(false));
    }
}
