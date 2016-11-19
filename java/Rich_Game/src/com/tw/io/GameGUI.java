package com.tw.io;

import com.tw.core.Game;
import com.tw.core.commands.Command;
import com.tw.core.commands.Response;
import com.tw.core.player.Player;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by pzzheng on 11/15/16.
 */
public class GameGUI {

    PrintStream out;
    private Scanner in;
    private UserInteractiveGameFactory gameFactory;

    public static void main(String[] args) {
        final Scanner in1 = new Scanner(System.in);
        GameGUI gameGUI = new GameGUI(System.out, in1, new UserInteractiveGameFactory(in1, System.out));
        gameGUI.startGame();
    }

    public GameGUI(PrintStream out, Scanner in, UserInteractiveGameFactory gameFactory) {
        this.out = out;
        this.in = in;
        this.gameFactory = gameFactory;
    }

    public void startGame() {
        out.println("请输入\"rich\"开始游戏:");
        if (in.next().toLowerCase().equals("rich")) {

            Game game = gameFactory.createGame();
            in.nextLine();
            String command = null;
            String response = null;
            do {
                MapGUI.flush(game.getMap());
                Player currentPlayer = game.currentPlayer();
                out.print(currentPlayer.getName() + ">");
                if (isQuit(command = in.nextLine())) return;

                if (currentPlayer.getStatus().equals(Player.Status.WAIT_FOR_COMMAND)) {
                    Command commandObj = Converter.convertToCommand(command, game.getMap());
                    if (commandObj == null) {
                        continue;
                    }
                    game.execute(commandObj);
                    if (game.getStatus().equals(Game.Status.GAME_END)) return;

                    while (currentPlayer.getStatus().equals(Player.Status.WAIT_FOR_RESPONSE)) {
                        Converter.showHint(currentPlayer.getResponseCommand());
                        if (isQuit(response = in.nextLine())) return;
                        Response responseObj = Converter.convertToResponse(response);
                        if (responseObj == null) continue;
                        game.respond(responseObj);
                    }
                }
            } while (true);
        }
    }

    private Game createGame(Scanner in) {
        return gameFactory.createGame();

    }

    private static boolean isQuit(String command) {
        return command.toLowerCase().equals("quit");
    }
}
