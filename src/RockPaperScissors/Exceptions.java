package RockPaperScissors;

public class Exceptions {
    public static class UnknownItem extends Exception {
        public UnknownItem(String item) { super("Unknown '" + item + "'!"); }
    }

    public static class PlayerIsNotFound extends Exception {
        public PlayerIsNotFound(String player) { super("Player '" + player + "' is not found!"); }
    }

    public static class MinSize extends Exception {
        public MinSize() { super("Minimum size of items must be 3!"); }
    }

    public static class NotEvenItemsCount extends Exception {
        public NotEvenItemsCount() { super("The number of elements must be even!"); }
    }

    public static class ConfigIsNotFound extends Exception {
        public ConfigIsNotFound(String path) { super("Config is not found of path '" + path + "'"); }
    }
}
