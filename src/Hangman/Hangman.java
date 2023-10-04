package Hangman;
import java.util.Scanner;


public class Hangman {
    public static Scanner In;
    public static void main(String[] args){
        while(PlayOrQuit()){
            Host host = new Host();
            while (true) {
                host.ConsoleShowWord();
                ResultOfGuessLetter result = host.Guess(GetUserChar());
                if(result.Win){
                    System.out.println("You won!");
                    break;
                }
                else if(result.Lose){
                    System.out.println("You lost!");
                    break;
                }
                if (result.UsedLetter) System.out.println("This letter has been used before!");
                else if (!result.GuessedLetter) {
                    System.out.println("That letter doesn't appear in the word");
                }
            }
        }
        System.out.println("Bye!");
    }

    static Character GetUserChar(){
        while(true){
            System.out.println("Input a letter:");
            String userString = In.nextLine();
            if(userString.length() == 1) return userString.charAt(0);
            System.out.println("The line must be the fourth in 1 character");
        }
    }

    static Boolean PlayOrQuit(){
        Hangman.In = new Scanner(System.in);
        System.out.println("Type \"play\" to play the game, \"exit\" to quit:");
        return switch (In.nextLine().toLowerCase()){
            case "play":
                yield  true;
            case "exit":
                yield  false;
            default:
                yield  PlayOrQuit();
        };
    }
}
