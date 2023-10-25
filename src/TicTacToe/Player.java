package TicTacToe;

public class Player{
    private final Character SYMBOL;
    public Player(Character symbol) { SYMBOL = symbol; }
    public Player() { SYMBOL = ' '; }
    public Character GetSymbol() { return SYMBOL; }

    @Override
    public boolean equals(Object obj){
        if(obj == this){return true;}
        if(obj == null || getClass() != obj.getClass()) { return false; }
        return SYMBOL == ((Player) obj).SYMBOL;
    }
}
