package com.tw.io;

import com.tw.core.Game;
import com.tw.core.commands.Command;
import com.tw.core.commands.Response;
import com.tw.core.player.Player;

import java.util.Scanner;

/**
 * Created by pzzheng on 11/15/16.
 */
public class GameGUI {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入\"rich\"开始游戏:");
        if (in.next().toLowerCase().equals("rich")) {
            Game game = new Game();
            System.out.println("设置玩家初始资金，范围1000～50000 (默认10000):");
            int initialFund = in.nextInt();
            System.out.println("请选择2~4位不重复玩家，输入编号即可(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝), eg: 12:");
            game.initPlayers(initialFund, Converter.convertToPlayers(in.next()));
            System.out.println("游戏开始,请按要求输入指令.");
            String command = null;
            String response = null;
            do {
                MapGUI.flush(game.getMap());
                Player currentPlayer = game.currentPlayer();
                System.out.print(currentPlayer.getName() + ">");
                if(isQuit(command = in.next())) return;

                if (currentPlayer.getStatus().equals(Player.Status.WAIT_FOR_COMMAND)) {
                    Command commandObj = Converter.convertToCommand(command, game.getMap());
                    if(commandObj == null) continue;
                    game.execute(commandObj);
                    if(game.getStatus().equals(Game.Status.GAME_END))   return;

                    while (currentPlayer.getStatus().equals(Player.Status.WAIT_FOR_RESPONSE)) {
                        Converter.showHint(currentPlayer.getResponseCommand());
                        if(isQuit(response = in.next())) return;
                        Response responseObj = Converter.convertToResponse(response);
                        if(responseObj == null) continue;
                        game.respond(responseObj);
                    }
                }


            } while (true);
        }
    }

    private static boolean isQuit(String command) {
        return command.toLowerCase().equals("quit");
    }
}
