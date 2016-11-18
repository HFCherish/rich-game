package com.tw.io;

import com.tw.core.DefaultDice;
import com.tw.core.GameHelp;
import com.tw.core.commands.*;
import com.tw.core.map.Estate;
import com.tw.core.map.GameMap;
import com.tw.core.toolHouse.Tool;
import com.tw.core.toolHouse.ToolType;

import java.util.*;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Converter {
    public static List<String> allPlayers = Arrays.asList("钱夫人", "阿土伯", "孙小美", "金贝贝");
    public static List<Tool> allTools = Arrays.asList(ToolType.Block, ToolType.RobotDull, ToolType.Bomb);

    public static List<Map<String, String>> convertToPlayers(String playerIds) {
        List<Map<String, String>> res = new ArrayList<>();
        for (char id : playerIds.toCharArray()) {
            res.add(new HashMap<String, String>() {{
                put("id", String.valueOf(id));
                put("name", allPlayers.get(id - '1'));
            }});
        }
        return res;
    }

    public static Command convertToCommand(String command, GameMap map) {
        command = command.toLowerCase();
        if (command.equals("roll")) return CommandFactory.Roll(DefaultDice.dice);
        if (command.equals("query")) return CommandFactory.Query;
        if (command.matches("block \\d")) {
            return CommandFactory.Block(Integer.valueOf(command.split(" ")[1]));
        }
        if (command.matches("bomb \\d")) {
            return CommandFactory.Bomb(Integer.valueOf(command.split(" ")[1]));
        }
        if (command.equals("robot")) return CommandFactory.RobotDull;
        if (command.matches("sell \\d")) {
            Integer index = Integer.valueOf(command.split(" ")[1]);
            Estate estate = map.getEstate(index);
            if(estate == null) {
                System.out.println("这处房产不为你所有, 请购买后再卖.");
                return null;
            }
            return CommandFactory.SellEstate(estate);
        }
        if (command.matches("selltool \\d")) {
            Integer index = Integer.valueOf(command.split(" ")[1]);
            if(index > 3 || index<1) {
                System.out.println("这个工具不存在.");
                return null;
            }
            return CommandFactory.SellTool(allTools.get(index -1));
        }
        if (command.equals("help")) {
            return CommandFactory.Help;
        }

        return null;
    }

    public static Response convertToResponse(String response) {
        response = response.toLowerCase();
        if (response.equals("y")) return Response.Yes;
        if (response.equals("y")) return Response.No;

        Integer number = Integer.valueOf(response);
        if (number != null) {
            return Response.Number(number);
        }
        return null;
    }

    public static void showHint(Responsive responseCommand) {
        if (responseCommand instanceof BuyEstate) {
            System.out.println("是否购买该处空地, " + ((BuyEstate) responseCommand).getEmptyEstate().getEmptyPrice() + "元 (Y/N) ?");
            return;
        }
        if (responseCommand instanceof UpgradeEstate) {
            System.out.println("是否升级该处地产, " + ((UpgradeEstate) responseCommand).getEstate().getEmptyPrice() + "元 (Y/N) ?");
            return;
        }
        if (responseCommand instanceof BuyTool) {
            System.out.println("欢迎光临道具屋， 请选择您所需要的道具:");
            showToolHouseInfo();
            return;
        }
        if (responseCommand instanceof SelectGift) {
            System.out.println("欢迎光临礼品屋，请选择一件您 喜欢的礼品:");
            showGiftHouseInfo();
            return;
        }
    }

    public static void showToolHouseInfo() {

    }

    public static void showGiftHouseInfo() {

    }
}
