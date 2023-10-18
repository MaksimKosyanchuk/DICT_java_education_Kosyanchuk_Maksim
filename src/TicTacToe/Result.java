package TicTacToe;

public class Result {
    public boolean Win = false;
    public boolean Draw = false;
    public Player Player;
    public Result(boolean win, Player player){
        Win = win;
        Player = player;
    }
    public Result(boolean draw) { Draw = draw; }
    public Result() { }
}
