package com.tw.io;

import com.tw.core.Game;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by pzzheng on 11/19/16.
 */
public class UserInteractiveGameFactory implements GameFactory {

    private final Scanner in;
    private final PrintStream out;

    public UserInteractiveGameFactory(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public Game createGame() {
        Game game = new Game();
        out.println("设置玩家初始资金，范围1000～50000 (默认10000):");
        int initialFund = in.nextInt();
        out.println("请选择2~4位不重复玩家，输入编号即可(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝), eg: 12:");
        game.initPlayers(initialFund, Converter.convertToPlayers(in.next()));
        out.println("游戏开始,请按要求输入指令.");
        return game;
    }
}
