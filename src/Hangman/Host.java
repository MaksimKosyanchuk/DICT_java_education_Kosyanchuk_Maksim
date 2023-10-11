package Hangman;
import java.util.ArrayList;
import java.util.Random;

class ResultOfGuessLetter{
    public Boolean UsedLetter;
    public Boolean GuessedLetter = false;
    public Boolean Win = false;
    public Boolean Lose = false;

    ResultOfGuessLetter(Boolean usedLetter){
        UsedLetter = usedLetter;
    }
    ResultOfGuessLetter(Boolean usedLetter, Boolean guessLetter){
        GuessedLetter = guessLetter;
        UsedLetter = usedLetter;
    }
    ResultOfGuessLetter(Boolean usedLetter, Boolean guessLetter, Boolean win){
        GuessedLetter = guessLetter;
        UsedLetter = usedLetter;
        Win = win;
    }
    ResultOfGuessLetter(Boolean usedLetter, Boolean guessLetter, Boolean win, Boolean lose){
        GuessedLetter = guessLetter;
        UsedLetter = usedLetter;
        Win = win;
        Lose = lose;
    }
}
class Host{
    private final ArrayList<String> WordsForGuess = new ArrayList<String>();
    private String WordForGuess;
    private int MistakeCount = 0;
    private static final int MAXMISTAKES = 8;
    private final ArrayList<Character> UsedCharacters = new ArrayList<Character>();
    private final ArrayList<Character> GuessedLetters = new ArrayList<Character>();

    Host(){
        this.WordsForGuess.add("python");
        this.WordsForGuess.add("java");
        this.WordsForGuess.add("javascript");
        this.WordsForGuess.add("typescript");
        this.WordsForGuess.add("php");
        this.WordsForGuess.add("kotlin");

        SetWordForGuess();
    }

    public void ConsoleShowWord(){
        StringBuilder word = new StringBuilder();
        for(Character character : WordForGuess.toCharArray()){
            if(!GuessedLetters.contains(character)) word.append("-");
            else word.append(character.toString());
        }
        System.out.println(word);
    }

    private void SetWordForGuess(){
        Random random = new Random();
        WordForGuess = WordsForGuess.get(random.nextInt(WordsForGuess.size()));
    }

    public ResultOfGuessLetter Guess(Character letter){
        if(this.UsedCharacters.contains(letter)) return new ResultOfGuessLetter(true);
        UsedCharacters.add(letter);
        if(WordForGuess.indexOf(letter) == -1){
            MistakeCount ++;
            if(MistakeCount == MAXMISTAKES) return new ResultOfGuessLetter(false, false,
                    false, true);
            return new ResultOfGuessLetter(false, false);
        }
        GuessedLetters.add(letter);
        return IsGuessedWord();
    }

    private ResultOfGuessLetter IsGuessedWord(){
        boolean won = WordForGuess.chars()
                .mapToObj(c -> (char) c)
                .allMatch(GuessedLetters::contains);
        return new ResultOfGuessLetter(false, true, won,false);
    }
}