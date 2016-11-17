package com.tw.core.commands;

import com.tw.core.player.Player;
import com.tw.core.report.StaticReport;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Query implements Command {
    @Override
    public Player.Status execute(Player player) {
        StaticReport.printReportAsString(player.getFunds(), player.getPoints(), player.getEstates(), player.getTools());
        return Player.Status.WAIT_FOR_COMMAND;
    }
}
