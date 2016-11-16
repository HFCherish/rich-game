package com.tw.core.player;

import com.tw.core.Player;
import com.tw.core.commands.Command;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/16/16.
 */
public class PlayerTest {
    @Test
    public void should_not_execute_command_if_not_wait_for_command() {
        Command command = mock(Command.class);
        when(command.execute(anyObject())).thenReturn(Player.Status.WAIT_FOR_TURN);
        Player player = new Player();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));

        assertThat(player.execute(command), is(Player.Status.WAIT_FOR_TURN));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }
}