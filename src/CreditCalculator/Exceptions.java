package CreditCalculator;

public class Exceptions {
    public static class IncorrectFormat extends Exception {
        public IncorrectFormat() {
            super("Incorrect parameter format!");
        }
    }

    public static class UnknownCreditType extends Exception {
        public UnknownCreditType(String type) {
            super("Unknown type '" + type + "' of credit!");
        }
    }

    public static class RequiredArgumentIsNotFound extends Exception {
        public RequiredArgumentIsNotFound(String arg) {
            super("Required argument '" + arg + "' is not found!");
        }
    }

    public static class FullDataGiven extends Exception {
        public FullDataGiven() {
            super("There is no arguments for calculation!");
        }
    }

    public static class GetHelp extends Exception {
        public GetHelp() {
            super();
        }
    }
}
