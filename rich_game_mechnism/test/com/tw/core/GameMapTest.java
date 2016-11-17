package com.tw.core;

import com.tw.core.assistentPower.Tool;
import com.tw.core.places.*;
import org.junit.Test;
import sun.dc.pr.PRError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/17/16.
 */
public class GameMapTest {

    @Test
    public void should_able_to_move_from_a_place_to_another_place() {
        Starting starting = new Starting();
        Estate estate = new Estate(10);
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        GameMap map = new GameMap(starting, estate, estate1, estate2);

        assertThat(map.move(starting, 1), is(estate));
        assertThat(map.move(starting, 5), is(estate));

        assertThat(map.move(starting, -1), is(estate2));
        assertThat(map.move(starting, -5), is(estate2));
    }

    @Test
    public void should_stop_moving_when_meet_block_or_bomb() {
        Starting starting = new Starting();
        Estate estate = new Estate(10);
        Prison prison = new Prison();
        Estate estate2 = new Estate(10);
        GameMap map = new GameMap(starting, estate, prison, estate2);

        prison.putTool(Tool.BLOCK);
        assertThat(prison.getToolOnThePlace(), is(Tool.BLOCK));
        assertThat(map.move(starting, 5) instanceof BlockPlace, is(true));
        assertThat(prison.getToolOnThePlace(), is(nullValue()));

        prison.putTool(Tool.BLOCK);
        assertThat(prison.getToolOnThePlace(), is(Tool.BLOCK));
        assertThat(map.move(starting, -3) instanceof BlockPlace, is(true));
        assertThat(prison.getToolOnThePlace(), is(nullValue()));

        prison.putTool(Tool.BLOCK);
        assertThat(prison.getToolOnThePlace(), is(Tool.BLOCK));
        assertThat(map.move(prison, -3) instanceof BlockPlace, is(true));
        assertThat(prison.getToolOnThePlace(), is(nullValue()));
    }

    @Test
    public void should_able_to_put_block_or_bomb_on_map() {
        Starting starting = new Starting();
        Estate estate = new Estate(10);
        Prison prison = new Prison();
        Estate estate2 = new Estate(10);
        GameMap map = new GameMap(starting, estate, prison, estate2);

        assertThat(map.putBlock(starting, 1), is(true));
        assertThat(estate.getToolOnThePlace(), is(Tool.BLOCK));

        assertThat(map.putBomb(starting, -5), is(true));
        assertThat(estate2.getToolOnThePlace(), is(Tool.BOMB));
    }

    @Test
    public void should_not_able_to_put_tool_if_invalid_steps_or_that_place_has_people_or_tool() {
        Starting starting = new Starting();
        Estate estate = new Estate(10);
        Prison prison = new Prison();
        Estate estate2 = new Estate(10);
        GameMap map = new GameMap(starting, estate, prison, estate2);
        Game game = new Game(map);
        Player player = Player.createPlayerWithGame_Fund_CommandState(game, 1000);
        game.initialPlayers(player);
        System.out.println(player.getCurrentPlace());

        assertThat(map.putBlock(starting, 15), is(false));

        map.putBomb(starting,1);
        assertThat(map.putBlock(starting, 1), is(false));
        assertThat(estate.getToolOnThePlace(), is(Tool.BOMB));

        assertThat(map.putBlock(starting, 0), is(false));
        assertThat(starting.getToolOnThePlace(), is(nullValue()));
    }
}