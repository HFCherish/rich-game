package com.tw.map;

import com.tw.Dice;
import com.tw.Game;
import com.tw.commands.CommandFactory;
import com.tw.player.Player;
import com.tw.toolHouse.ToolType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by pzzheng on 11/14/16.
 */
public class DefaultGameMapTest {
    Game game = mock((Game.class));

    @Test
    public void should_able_to_move_to_another_place() {
        Place starting = new Estate(10000);
        Place estate2 = new Estate(10000);
        Place estate3 = new Estate(10000);
        Place estate4 = new Estate(10000);
        GameMap map = new DefaultGameMap(2, 2, starting, estate2, estate3, estate4);

        assertThat(map.move(starting, 1), is(estate2));

        assertThat(map.move(starting, 2), is(estate3));

        assertThat(map.move(starting, 4), is(starting));

        assertThat(map.move(starting, -1), is(estate4));

        assertThat(map.move(starting, -4), is(starting));

        assertThat(map.move(starting, -3), is(estate2));
    }

    @Test
    public void should_stop_if_pass_block_or_bomb_when_move() {
        Place starting = new Estate(10000);
        Place bomb = new BombPlace(new Prison());
        Place estate3 = new Estate(10000);
        Place estate4 = new Estate(10000);
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, bomb, estate3, estate4);

        assertThat(map.move(starting, 3), is(bomb));

        map.places.set(1, bomb);
        assertThat(map.move(starting, 6), is(bomb));

        map.places.set(1, bomb);
        assertThat(map.move(starting, -6), is(bomb));
    }

    @Test
    public void should_set_tool_to_the_right_place() {
        Place starting = new Estate(10000);
        Place estate2 = new Estate(10000);
        Place estate3 = new Estate(10000);
        Place estate4 = new Estate(10000);
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, estate2, estate3, estate4);

        assertThat(map.places.get(1) instanceof BombPlace, is(false));

        assertThat(map.setTool(ToolType.Bomb, 1, starting), is(true));

        assertThat(map.places.get(1) instanceof BombPlace, is(true));
    }

    @Test
    public void should_clean_the_other_tools_around_10_if_use_robot() {
        Place starting = new Estate(10000);
        Place bomb = new BombPlace(new Estate(10000));
        Place estate3 = new Estate(10000);
        Place block = new BlockPlace(new Prison());
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, bomb, estate3, block);

        assertThat(map.places.get(1) instanceof BombPlace, is(true));
        assertThat(map.places.get(3) instanceof BlockPlace, is(true));

        assertThat(map.setTool(ToolType.RobotDull, 0, starting), is(true));

        assertThat(map.places.get(1) instanceof Estate, is(true));
        assertThat(map.places.get(3) instanceof Prison, is(true));
    }

    @Test
    public void should_not_set_tool_to_a_place_contains_tool() {
        Place starting = new Estate(10000);
        Place bomb = new BombPlace(new Prison());
        Place estate3 = new Estate(10000);
        Place estate4 = new Estate(10000);
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, bomb, estate3, estate4);

        assertThat(map.places.get(1) instanceof BombPlace, is(true));

        assertThat(map.setTool(ToolType.Bomb, 1, starting), is(false));

        assertThat(map.places.get(1) instanceof BombPlace, is(true));
    }

    @Test
    public void should_not_set_tool_to_a_place_contains_player() {
        Place starting = new Estate(10000);
        Place bomb = new BombPlace(new Prison());
        Place estate3 = new Estate(10000);
        Place estate4 = new Estate(10000);
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, bomb, estate3, estate4);
        Player.createPlayerWith_Fund_Map_place_command_state_in_game(map, 10000, game, estate3);

        assertThat(map.setTool(ToolType.Bomb, 2, starting), is(false));
    }

    @Test
    public void should_go_to_hospital_and_remove_bomb_after_pass_the_bomb_place() {
        Place starting = new Estate(10000);
        Prison prison = new Prison();
        Place bomb = new BombPlace(prison);
        Place estate3 = new Estate(10000);
        Place hospital = new Hospital();
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, bomb, estate3, hospital);
        Player player = Player.createPlayerWith_Fund_Map_place_command_state_in_game(map, 10000, game, estate3);
        Dice dice = () -> 3;

        assertThat(player.getCurrentPlace(), is(estate3));
        assertThat(map.places.get(1), is(bomb));

        CommandFactory.Roll(dice).execute(player);

        assertThat(player.getCurrentPlace(), is(hospital));
        assertThat(map.places.get(1), is(prison));
    }

    @Test
    public void should_go_to_the_place_where_the_block_is_put_and_remove_block_after_pass_the_block_place() {
        Place starting = new Estate(10000);
        Prison prison = new Prison();
        Place block = new BlockPlace(prison);
        Place estate3 = new Estate(10000);
        Place hospital = new Hospital();
        DefaultGameMap map = new DefaultGameMap(2, 2, starting, block, estate3, hospital);

        Player player = Player.createPlayerWith_Fund_Map_place_command_state_in_game(map, 10000, game, estate3);
        Dice dice = () -> 3;

        assertThat(player.getCurrentPlace(), is(estate3));
        assertThat(map.places.get(1), is(block));

        CommandFactory.Roll(dice).execute(player);

        assertThat(player.getCurrentPlace(), is(prison));
        assertThat(player.getStuckDays(), is(2));
        assertThat(map.places.get(1), is(prison));
    }
}