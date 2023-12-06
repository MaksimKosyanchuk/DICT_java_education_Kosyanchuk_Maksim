package CoffeeMachine;

public class Exceptions {
    public static class Exit extends Exception {
        public Exit() {
            super();
        }

        public Exit(String message) {
            super(message);
        }
    }
    public static class EnoughResources extends Exception{
        public EnoughResources(String resource) {
            super("Sorry, not enough " + resource + "!");
        }
    }
}
