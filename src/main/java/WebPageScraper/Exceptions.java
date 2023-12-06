package WebPageScraper;

public class Exceptions {
    public static class InvalidRequest extends Exception {
        public InvalidRequest(String url) {
            super("Invalid request for: '" + url + "'");
        }
    }

    public static class NothingIsFound extends Exception {
        public NothingIsFound() {
            super("Nothing is found on page!");
        }
    }
}
