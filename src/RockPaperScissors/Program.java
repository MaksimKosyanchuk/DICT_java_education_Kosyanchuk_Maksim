package RockPaperScissors;

import RockPaperScissors.Players.Computer;
import RockPaperScissors.Players.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static Scanner In;

    public static void main(String[] args) {
        In = new Scanner(System.in);
        Rating.Start();
        var user = new User();
        var userItems = GetUserItems();
        try {
            while (true) {
                switch (GetAction()) {
                    case "exit":
                        return;
                    case "rating":
                        GetRating(user);
                        break;
                    case "play":
                        Play(user, userItems);
                        break;
                    default:
                        System.out.println("Unknown command!");
                }
            }
        }
        catch(Exception e) { System.out.println(e.getMessage()); }
    }

    private static ArrayList<String> GetUserItems() {
        System.out.println("Enter items: ");
        return new ArrayList<>(Arrays.asList(In.nextLine().split(",")));
    }

    private static String GetAction() {
        System.out.println("What do you want?\n-Exit\n-Rating\n-Play");
        return In.nextLine().toLowerCase();
    }

    private static void GetRating(User player) {  System.out.println(player.GetRating()); }

    private static void Play(User user, ArrayList<String> userItems)
        throws Exceptions.MinSize, Exceptions.NotEvenItemsCount, java.io.IOException, Exceptions.ConfigIsNotFound {
        new Round(user, new Computer(), userItems).Start();
    }
}
