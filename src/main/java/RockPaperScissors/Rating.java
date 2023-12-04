package RockPaperScissors;

import RockPaperScissors.Players.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class Rating {
    private static final String FileName = "rating.txt";
    private static final String DirName = "./src/RockPaperScissors/";
    private static final Path FilePath = Paths.get(DirName + FileName);
    private static final Path DirPath = Paths.get(DirName);
    public static void Start() {
        CheckDirectoryExists();
        CheckFileExists();
    }

    private static void CheckDirectoryExists(){
        try {
            if(!Files.exists(DirPath)) { Files.createDirectory(DirPath); }
        }
        catch(java.io.IOException ignored) { }
    }

    private static void CheckFileExists() {
        var file = new File(DirName, FileName);
        if(!file.exists()) { CreateFile(); }
    }

    private static void CreateFile() {
        try { Files.createFile(FilePath);}
        catch(IOException e) { System.out.println(e.getMessage()); }
    }

    public static int GetPlayerRating(User player) throws Exceptions.PlayerIsNotFound {
        var rating = GetRating();
        var value = rating.get(player.GetName());
        if(value == null) { throw new Exceptions.PlayerIsNotFound(player.GetName()); }
        return value;
    }

    public static void AddRatingForPlayer(User player, int value) {
        int newValue;
        try {
            var userValue = GetPlayerRating(player);
            newValue = userValue + value;
        }
        catch(Exceptions.PlayerIsNotFound e) { newValue = 0; }
        var data = GetRating();
        data.put(player.GetName(), newValue);
        var sb = new StringBuilder();
        for(var item : data.keySet()) {
            sb.append(item);
            sb.append(": ");
            sb.append(data.get(item));
            sb.append("\n");
        }
        try(var writer = new FileWriter(FilePath.toString())){
            writer.write(sb.toString());
            writer.flush();
       }
        catch(IOException ignored) { }
    }

    public static HashMap<String, Integer> GetRating() {
        HashMap<String, Integer> rating = new HashMap<>();
        try{
            var data = Files.readAllLines(FilePath);
            for(var item : data) {
                var itemData = item.split(": ");
                rating.put(itemData[0], Integer.parseInt(itemData[1]));
            }
        }
        catch(IOException ignored) { }
        return rating;
    }
}
