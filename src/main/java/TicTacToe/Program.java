package TicTacToe;

import java.util.Scanner;

public class Program {
    public static Scanner In;
    public static void main(String[] args) {
        In = new Scanner(System.in);
        var core = new GameCore();
        core.StartGame();
    }
}
