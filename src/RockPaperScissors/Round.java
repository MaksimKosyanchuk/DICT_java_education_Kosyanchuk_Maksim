package RockPaperScissors;

import RockPaperScissors.Players.Computer;
import RockPaperScissors.Players.User;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Round {
    private ArrayList<String> Data;
    private static final int WIN_POINT = 100;
    private static final int DRAW_POINT = 50;
    private static final int LOSE_POINT  = 0;
    private final User FirstPlayer;
    private final Computer SecondPlayer;

    public Round(User firstPlayer, Computer secondPlayer, ArrayList<String> data)
        throws java.io.IOException, Exceptions.MinSize, Exceptions.NotEvenItemsCount, Exceptions.ConfigIsNotFound {
        SetData(data);
        FirstPlayer = firstPlayer;
        SecondPlayer = secondPlayer;
    }

    private void SetData(ArrayList<String> userData)
        throws java.io.IOException, Exceptions.MinSize, Exceptions.NotEvenItemsCount, Exceptions.ConfigIsNotFound {
        Data = new ArrayList<>();
        List<String> data;
        try{
            data = Files.readAllLines(Paths.get("src/RockPaperScissors/config.cfg"));
        }
        catch(NoSuchFileException file) {
            throw new Exceptions.ConfigIsNotFound(file.getMessage());
        }
        for(var item : data) {
            if(userData.contains(item)) {
                Data.add(item);
            }
        }

        if(Data.size() < 3) { throw new Exceptions.MinSize(); }

        if(Data.size() % 2 == 0) { throw new Exceptions.NotEvenItemsCount(); }
    }

    private static ArrayList<String> SortData(ArrayList<String> data, String option) {
        ArrayList<String> result = new ArrayList<>(data);
        result.remove(option);
        Collections.rotate(result, (result.size() / 2) + 1);
        result.add(result.size() / 2, option);
        return result;
    }

    public void Start() { CheckResult(FirstPlayer.DoMove(Data), SecondPlayer.DoMove(Data)); }

    public void CheckResult(String userMove, String computerMove) {
        var newData = SortData(Data, userMove);
        var userIndex = newData.indexOf(userMove);
        var computerIndex = newData.indexOf(computerMove);
        if(userIndex == computerIndex) { Draw(computerMove); }
        else if(userIndex > computerIndex) { UserWin(computerMove); }
        else { UserLose(computerMove); }
    }

    public void UserWin(String computerMove) {
        FirstPlayer.AddRating(WIN_POINT);
        System.out.println("Well done. The computer chose '" + computerMove + "' and failed!");
    }

    public void Draw(String userMove) {
        FirstPlayer.AddRating(DRAW_POINT);
        System.out.println("There is draw (" + userMove + ")!");
    }

    public void UserLose(String computerMove) {
        FirstPlayer.AddRating(LOSE_POINT);
        System.out.println("Sorry, but the computer chose '" + computerMove + "'!");
    }
}
