package WebPageScraper;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        var In = new Scanner(System.in);
        var core = new ProgramCore();
        System.out.println(core.parsePage(getPagesNumber(In), getArticleType(In)));
    }

    private static Integer getPagesNumber(Scanner in) {
        try{
            System.out.println("Enter number of pages:");
            return Integer.parseInt(in.nextLine());
        }
        catch(NumberFormatException e) { return getPagesNumber(in); }
    }

    private static String getArticleType(Scanner in) {
        System.out.println("Enter type of article:");
        return in.nextLine();
    }
}
