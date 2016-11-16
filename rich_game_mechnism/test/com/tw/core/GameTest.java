package com.tw.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
        Player player = new Player();
        Player player1 = new Player();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_TURN));

        game.initialPlayers(player, player1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_able_to_end_game_manual() {
        Game game = new Game(map);

        assertThat(game.getStatus(), is(Game.Status.START));

        game.quit();

        assertThat(game.getStatus(), is(Game.Status.END));
    }
}