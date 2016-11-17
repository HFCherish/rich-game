package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/16/16.
 */
public class PlayerTest {
    public static final int INITIAL_FUND = 10000;
    private GameMap map;
    private Game game;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
        game = new Game(map);
    }
    @Test
    public void should_can_execute_command_when_waiting_for_command() {
        Command command = mock(Command.class);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        when(command.execute(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);

        player.execute(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_can_respond_command_when_waiting_for_response() {
        Command command = mock(Command.class);
        Response response = mock(Response.class);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, INITIAL_FUND);

        when(command.execute(anyObject())).thenReturn(Player.Status.WAIT_FOR_RESPONSE);
        player.execute(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        when(command.respond(anyObject(), anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);

        player.respond(response);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }
}