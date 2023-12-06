package RockPaperScissors.Players;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {
    public Computer() { SetName(); }

    public void SetName() { Name = "Computer"; }

    public String DoMove(ArrayList<String> data) { return data.get(new Random().nextInt(data.size())); }
}
