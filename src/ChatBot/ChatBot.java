package ChatBot;
import java.util.Scanner;

public class ChatBot {
    private static String UserName;
    public static Scanner In;

    public static void main(String[] args){
        In = new Scanner(System.in);
        System.out.println("Hello! My name is Bot.\n" +
                "I was created in 2023.");
        GetUserName();
        System.out.println("What a great name you have, " + UserName + "!");
    }

    private static void GetUserName(){
        System.out.println("Please, remind me your name:");
        UserName = In.nextLine();
    }
}
