package com.tw.core.commands;

import com.tw.core.Asset;
import com.tw.core.Player;
import com.tw.core.places.Estate;
import com.tw.core.responses.Response;
import com.tw.core.assistentPower.Tool;

import java.util.List;

/**
 * Created by pzzheng on 11/17/16.
 */
public class Query implements Command {
    @Override
    public Player.Status execute(Player player) {
        Report.printReportAsString(player);
        return player.waitForCommand();
    }

    @Override
    public Player.Status respond(Response response, Player player) {
        return null;
    }

    public static class Report {

        private static StringBuilder res;

        public static String printReportAsString(Player player) {
            Asset asset = player.getAsests();
            res = new StringBuilder();
            res.append("资金: " + asset.getFunds() + "元\n");
            res.append("点数: " + asset.getPoints() + "点\n");
            appendEstateInfo(asset.getEstates());
            appendToolInfo(asset);
            System.out.println(res.toString());
            return res.toString();
        }

        private static void appendToolInfo(Asset asset) {
            int bomb = asset.getToolCount(Tool.BOMB);
            int block = asset.getToolCount(Tool.BLOCK);
            int robotDull = asset.getToolCount(Tool.ROBOT_DULL);
            res.append("道具: 路障" + block + "个; 炸弹" + bomb + "个; 机器娃娃" + robotDull + "个\n");
        }

        private static void appendEstateInfo(final List<Estate> estates) {
            int emptyHouse = 0;
            int thatchHouse = 0;
            int foreignStyleHouse = 0;
            int skyscraper = 0;
            for(Estate estate: estates) {
                if(estate.getLevel().equals(Estate.Level.EMPTY))  emptyHouse++;
                if(estate.getLevel().equals(Estate.Level.THATCH))  thatchHouse++;
                if(estate.getLevel().equals(Estate.Level.FOREIGN_STYLE))  foreignStyleHouse++;
                if(estate.getLevel().equals(Estate.Level.SKYSCRAPER))  skyscraper++;
            }
            res.append("地产: 空地" + emptyHouse + "处; 茅屋" + thatchHouse + "处; 洋房" + foreignStyleHouse + "处; 摩天楼" + skyscraper + "处\n");
        }

    }
}
