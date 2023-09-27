package ChatBot;
import java.awt.desktop.SystemEventListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.ArrayList;

public class ChatBot {
    private static String UserName;
    public static Scanner In;

    private static ArrayList<Integer> UserNumbers = new ArrayList<>();
    private static int AgeUser;

    private static int UserNumber;

    public static void main(String[] args){
        In = new Scanner(System.in);
        System.out.println("Hello! My name is Bot.\n" +
                "I was created in 2023.");
        GetUserName();
        System.out.println("What a great name you have, " + UserName + "!");
        GetUserNumbers();
        System.out.println("Your age is " + AgeUser + "; that`s a good time to start programming!");
        System.out.print("Now I will prove to you that I can count to any number you want");
        GetUserNumber();
        ConsoleOutUserNumber();
        TestHandle();
        System.out.println("Goodbye " + UserName + ", have a nice day!");
    }

    private static void TestHandle(){
        while(true){
            System.out.println("what is the best programming language?");
            System.out.println("1. C#");
            System.out.println("2. Python");
            System.out.println("3. Kotlin");
            if(In.nextInt() == 1){
                System.out.println("Correct!");
                break;
            }
            System.out.println("Wrong!");
        }
    }

    private static void ConsoleOutUserNumber(){
        for(int i = 0; i <= UserNumber; i++){
            System.out.println(Integer.toString(i) + "!");
        }
    }

    private static void GetUserNumber(){
        UserNumber = In.nextInt();
    }

    private static void GetUserNumbers(){
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7:");
        for(int i = 0; i < 3; i++){
            UserNumbers.add(In.nextInt());
        }
        AgeUser = (UserNumbers.get(0) * 70 + UserNumbers.get(1) * 21 + UserNumbers.get(2) * 15) % 105;
    }

    private static void GetUserName(){
        System.out.println("Please, remind me your name:");
        UserName = In.nextLine();
    }
}
