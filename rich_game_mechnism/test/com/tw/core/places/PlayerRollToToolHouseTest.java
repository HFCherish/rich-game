package com.tw.core.places;

import com.tw.core.Dice;
import com.tw.core.Game;
import com.tw.core.GameMap;
import com.tw.core.Player;
import com.tw.core.commands.Command;
import com.tw.core.commands.CommandFactory;
import com.tw.core.commands.Roll;
import com.tw.core.tools.Tool;
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
        toolHouse = new ToolHouse();
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
}
