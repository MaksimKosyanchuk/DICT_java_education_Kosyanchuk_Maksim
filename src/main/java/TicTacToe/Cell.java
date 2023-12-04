package TicTacToe;

public class Cell{
    public boolean Occupied;
    private Player Player;
    public Cell(){
        Occupied = false;
        SetNewPlayer(new Player());
    }

    public void SetNewPlayer(Player player) { Player = player; }

    public Player GetPlayer() { return Player; }

    public Character GetSymbol() { return Player.GetSymbol(); }
}