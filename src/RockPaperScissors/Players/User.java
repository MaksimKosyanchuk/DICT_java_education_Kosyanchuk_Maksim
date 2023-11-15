package RockPaperScissors.Players;

import RockPaperScissors.Exceptions;
import RockPaperScissors.Program;

import java.util.ArrayList;

public class User extends Player {
    private Integer Rating;

    public User() {
        SetName();
        SetRating();
        System.out.println("Hello, " + GetName() + "!");
        System.out.println("Your rate: " + GetRating() + "!");
    }

    public void SetName() {
        System.out.println("Enter your name:");
        Name = Program.In.nextLine().toLowerCase();
    }

    public String DoMove(ArrayList<String> data) { return GetUserPosition(data); }

    private String GetUserPosition(ArrayList<String> data) {
        while(true) {
            System.out.println("Enter your position:");
            var userPosition = Program.In.nextLine().toLowerCase();
            if(data.contains(userPosition)) { return userPosition; }
            System.out.println("Unknown position '" + userPosition + "'!");
        }
    }

    public void AddRating(int value) {
        RockPaperScissors.Rating.AddRatingForPlayer(this, value);
        SetRating();
    }

    private void SetRating() {
        try{
            Rating = RockPaperScissors.Rating.GetPlayerRating(this);
        }
        catch(Exceptions.PlayerIsNotFound e) {
            Rating = 0;
            RockPaperScissors.Rating.AddRatingForPlayer(this, 0);
        }
    }

    public Integer GetRating() { return Rating; }
}