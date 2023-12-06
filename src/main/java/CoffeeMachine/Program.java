package CoffeeMachine;
import java.util.Scanner;

public class Program {
    public static Scanner In;

    public static void main(String[] args){
        In = new Scanner(System.in);
        var Machine = new CoffeeMachine(new Coffee.Espresso(), new Coffee.Latte(), new Coffee.Cappuccino());
        boolean shouldExit = false;
        while(!shouldExit){
            try{
                String action = GetAction();
                shouldExit = (boolean)Machine.getClass().getMethod(action).invoke(Machine);
            }
            catch(NullPointerException e){
                // Ignore
            }
            catch (NoSuchMethodException e){
                System.out.println("Wrong!");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static String GetAction(){
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action = In.nextLine();
        char[] chars = action.toCharArray();
        return String.valueOf(chars[0]).toUpperCase() + action.substring(1);
    }
}