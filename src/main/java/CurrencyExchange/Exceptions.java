package CurrencyExchange;

public class Exceptions {
    public static class UnknownCurrency extends Exception {
        public UnknownCurrency() {
            super("Unknown currency!");
        }
    }

    public static class CurrencyIsNotInCache extends Exception {
        public CurrencyIsNotInCache() {
            super("Currency is not in the cache!");
        }
    }
}
