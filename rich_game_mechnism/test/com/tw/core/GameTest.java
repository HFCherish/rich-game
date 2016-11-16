package com.tw.core;

import com.tw.core.commands.Command;
import com.tw.core.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/16/16.
 */
public class GameTest {
    GameMap map;

    @Before
    public void setUp() {
        map = mock(GameMap.class);
    }

    @Test
    public void should_the_first_player_is_the_present_player() {
        Game game = new Game(map);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);

        game.initialPlayers(player, player1);

        assertThat(game.currentPlayer(), is(player));
    }

    @Test
    public void should_able_to_end_game_manual() {
        Game game = new Game(map);

        assertThat(game.getStatus(), is(Game.Status.START));

        game.quit();

        assertThat(game.getStatus(), is(Game.Status.END));
    }

    @Test
    public void should_game_end_if_only_one_player_not_bankrupt() {
        Game game = new Game(map);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);
        assertThat(game.getStatus(), is(Game.Status.START));

        when(player.getStatus()).thenReturn(Player.Status.BANKRUPT);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);

        assertThat(game.getStatus(), is(Game.Status.END));
    }

    @Test
    public void should_can_execute_command_if_current_player_wait_for_command() {
        Game game = new Game(map);
        Command command = mock(Command.class);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player.execute(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);

        assertThat(game.execute(command), is(Player.Status.WAIT_FOR_TURN));
        assertThat(game.lastCommand, is(command));
    }

    @Test
    public void should_can_not_execute_command_if_current_player_not_waiting_for_command() {
        Game game = new Game(map);
        Command command = mock(Command.class);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_RESPONSE);
        when(player.execute(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);

        assertThat(game.execute(command), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(game.lastCommand, is(not(command)));
    }

    @Test
    public void should_can_respond_if_current_player_wait_for_response() {
        Game game = new Game(map);
        Response response = mock(Response.class);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_RESPONSE);
        when(player.respond(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);

        assertThat(game.respond(response), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_can_not_respond_if_current_player_not_waiting_for_response() {
        Game game = new Game(map);
        Response response = mock(Response.class);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player.respond(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);

        assertThat(game.respond(response), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_able_to_shift_player() {
        Game game = new Game(map);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        assertThat(game.currentPlayer(), is(player));
        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);

        game.nextPlayer();

        assertThat(game.currentPlayer(), is(player1));
    }

    @Test
    public void should_shift_to_player_after_next_player_if_next_player_bankrupt() {
        Game game = new Game(map);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player1.getStatus()).thenReturn(Player.Status.BANKRUPT);
        assertThat(game.currentPlayer(), is(player));

        game.nextPlayer();

        assertThat(game.currentPlayer(), is(player));
    }

    @Test
    public void should_shift_to_player_after_next_player_if_next_player_in_hospital_or_prison() {
        Game game = new Game(map);
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        game.initialPlayers(player, player1);

        when(player.getStatus()).thenReturn(Player.Status.WAIT_FOR_COMMAND);
        when(player1.getStatus()).thenReturn(Player.Status.WAIT_FOR_TURN);
        when(player1.getStuckDays()).thenReturn(1);
        assertThat(game.currentPlayer(), is(player));

        game.nextPlayer();

        assertThat(game.currentPlayer(), is(player));
    }

}