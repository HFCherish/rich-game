package com.tw.player;

import com.tw.Dice;
import com.tw.asest.AssistancePower;
import com.tw.map.Block;
import com.tw.map.Bomb;
import com.tw.map.Estate;
import com.tw.map.GameMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by pzzheng on 11/13/16.
 */
public class PlayerQueryTest {
    public static final int INITIAL_FUND_10 = 10;
    public static final int EMPTY_PRICE_5 = 5;
    private Player currentPlayer;

    @Test
    public void should_show_right_asset_report() {
        GameMap map = mock(GameMap.class);
        Dice dice = () -> 1;

        currentPlayer = Player.createPlayerWith_Fund_Map_Tools_COMMAND_STATE(map, INITIAL_FUND_10 * 2, new Bomb(), new Block());
        Estate emptyEstate = new Estate(EMPTY_PRICE_5);
        currentPlayer.buyEstate(emptyEstate);
        Estate thatch = new Estate(EMPTY_PRICE_5);
        thatch.upgrade();
        currentPlayer.buyEstate(thatch);

        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        String reportResult = currentPlayer.queryAsString(new DefaultReport());
        assertThat(reportResult, containsString("资金: " + INITIAL_FUND_10 + "元"));
        assertThat(reportResult, containsString("点数: 0点"));
        assertThat(reportResult, containsString("空地1处"));
        assertThat(reportResult, containsString("茅屋1处"));
        assertThat(reportResult, containsString("洋房0处"));
        assertThat(reportResult, containsString("摩天楼0处"));
        assertThat(reportResult, containsString("路障1个"));
        assertThat(reportResult, containsString("炸弹1个"));
        assertThat(reportResult, containsString("机器娃娃0个"));
        assertThat(currentPlayer.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }
}
