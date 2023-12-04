package TicTacToe;

import com.sun.nio.sctp.AssociationChangeNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameCore {
    private int GameFieldSize;
    private final Cell[][] GameField;
    private Player CurrentPlayer;
    private final Player FirstPlayer;
    private final Player SecondPlayer;
    public GameCore(){
        SetGameFieldSize();
        FirstPlayer = new Player('X');
        SecondPlayer = new Player('0');
        CurrentPlayer = FirstPlayer;
        GameField = new Cell[GameFieldSize][GameFieldSize];
        for (int row = 0; row < GameFieldSize; row++) {
            for (int col = 0; col < GameFieldSize; col++) {
                GameField[row][col] = new Cell();
            }
        }
    };

    private void SetGameFieldSize(){
        while(true){
            System.out.println("Enter the size of game field: ");
            var size = Program.In.nextLine();
            try{
                var gameFieldSize = Integer.parseInt(size);
                if(gameFieldSize < 2 || gameFieldSize > 10){
                    System.out.println("Enter number in range 1; 10");
                    continue;
                }
                GameFieldSize = gameFieldSize;
                return;
            }
            catch(NumberFormatException e){
                System.out.println("Enter the number!");
            }
        }
    }

    public void StartGame(){
        PrintGameField();
        while(true){
            DoMove();
            PrintGameField();
            var result = CheckResult();
            if(result.Win){
                Win(result.Player);
                break;
            }
            if(result.Draw){
                Draw();
                break;
            }
            ChangePlayer();
        }
    }

    private void Win(Player player) { System.out.println(player.GetSymbol().toString() + " Won!"); }

    private void Draw() { System.out.println("Draw!"); }

    private void DoMove(){
        var cords = GetCordsFromPlayer();
        if(IsCellOccupied(cords)) {
            DoMove();
            return;
        }
        GameField[cords[0]][cords[1]].Occupied = true;
        GameField[cords[0]][cords[1]].SetNewPlayer(CurrentPlayer);
    }

    private boolean IsCellOccupied(int[] cords) { return GameField[cords[0]][cords[1]].Occupied; }

    private int[] GetCordsFromPlayer(){
        overLoop:while(true){
            var Cords = new int[GameFieldSize];
            System.out.println("Player \"" + CurrentPlayer.GetSymbol().toString() +  "\" Enter the coordinates:");
            try{
                var cords = Program.In.nextLine().split(" ");
                if(cords.length != 2) continue;
                for(var i = 0; i < cords.length; i++){
                    int cord = Integer.parseInt(cords[i]);
                    if(cord < 1 || cord > GameFieldSize){
                        System.out.println("Coordinates should be from 1 to " + GameFieldSize + "!");
                        continue overLoop;
                    }
                    Cords[i] = cord - 1;
                }
            }
            catch(NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            return Cords;
        }
    }

    private Result CheckResult(){
        var count = 0;
        Player player = null;
        for (int i = 0; i < GameFieldSize; i++)
        {
            for (int j = 0; j < GameFieldSize; j++)
            {
                if (GameField[i][j].Occupied && GameField[i][j].GetPlayer() == GameField[i][0].GetPlayer())
                {
                    player = GameField[i][0].GetPlayer();
                    count++;
                }
            }
            if (count == GameFieldSize)
            {
                return new Result(true, player);
            }

            player = null;
            count = 0;
        }
        for (int i = 0; i < GameFieldSize; i++)
        {
            for (int j = 0; j < GameFieldSize; j++)
            {
                if (GameField[j][i].Occupied && GameField[j][i].GetPlayer() == GameField[0][i].GetPlayer())
                {
                    count++;
                    player = GameField[0][i].GetPlayer();
                }
            }
            if (count == GameFieldSize)
            {
                return new Result(true, player);
            }

            player = null;
            count = 0;
        }

        for (int i = 0; i < GameFieldSize; i++)
        {
            if (GameField[i][i].Occupied && GameField[i][i].GetPlayer() == GameField[0][0].GetPlayer())
            {
                count++;
                player = GameField[0][0].GetPlayer();
            }
        }
        if (count == GameFieldSize)
        {
            return new Result(true, player);
        }

        count = 0;
        player = null;
        for (int i = 0; i < GameFieldSize; i++)
        {
            int len = GameField[i].length - 1;
            if (GameField[i][len - i].Occupied && GameField[i][len - i].GetPlayer() == GameField[0][len].GetPlayer())
            {
                player = GameField[0][len - i].GetPlayer();
                count++;
            }
        }
        if (count == GameFieldSize)
        {

            return new Result(true, player);
        }

        boolean draw = Arrays.stream(GameField)
                .allMatch(row -> Arrays.stream(row)
                        .allMatch(cell -> cell.Occupied));

        return new Result(draw);
    }

    private void PrintGameField() { System.out.println(BuildGameField()); }

    private String BuildGameField() {
        StringBuilder board = new StringBuilder();
        board.append("┌");
        for (int i = 0; i < GameFieldSize; i++) {
            board.append("─────");
            if (i < GameFieldSize - 1) {
                board.append("┬");
            }
        }
        board.append("┐\n");

        for (int row = 0; row < GameFieldSize; row++) {
            for(int col = 0; col < GameFieldSize; col++){
                board.append("│  ");
                board.append(GameField[row][col].GetSymbol().toString());
                board.append("  ");
            }
            board.append("│\n");
            if(row == GameFieldSize - 1){
                board.append("└");
                for(int col = 0; col < GameFieldSize; col++){
                    board.append("─────");
                    if(col != GameFieldSize - 1){
                        board.append("┴");
                    }
                }
                board.append("┘\n");
            }
            else{
                for(int col = 0; col < GameFieldSize; col++){
                    if(col == 0) board.append("├");
                    else board.append("┼");
                    board.append("─────");
                }
                board.append("┤\n");
            }
        }

        return board.toString();
    }

    private void ChangePlayer() { CurrentPlayer = CurrentPlayer == FirstPlayer ? SecondPlayer : FirstPlayer; }
}
