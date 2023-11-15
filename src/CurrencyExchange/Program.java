package CurrencyExchange;

import CurrencyExchange.cache.Cache;
import CurrencyExchange.parser.Parser;

import java.util.Scanner;

public class Program {
    public static Scanner In = new Scanner(System.in);

    public static void main(String[] args) {
        var firstCurrency = GetCurrency();
        while(true) {
            var secondCurrency = GetCurrency();
            if(secondCurrency.equals("exit")) { return; }
            try{
                System.out.println("Checking in cache...");
                Print(new Cache().GetValue(firstCurrency, secondCurrency), secondCurrency);
            }
            catch (Exceptions.CurrencyIsNotInCache e) {
                try{
                    System.out.println("Sorry, but it is not in the cache!");
                    var value = new Parser().GetCurrency(firstCurrency, secondCurrency);
                    Print(value, secondCurrency);
                    new Cache().addElement(firstCurrency, secondCurrency, value);
                }
                catch(Exceptions.UnknownCurrency ex) { System.out.println(ex.getMessage()); }
            }
        }
    }

    private static String GetCurrency() {
        return In.nextLine().toLowerCase();
    }

    private static void Print(Double value, String currency) {
        System.out.println("You received " + value + " " + currency.toUpperCase() + "!");
    }
}
