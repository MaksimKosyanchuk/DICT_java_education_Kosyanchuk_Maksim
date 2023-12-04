package RockPaperScissors.Players;

import java.util.ArrayList;

public interface IPlayer {
    void SetName();

    String DoMove(ArrayList<String> data);
}
