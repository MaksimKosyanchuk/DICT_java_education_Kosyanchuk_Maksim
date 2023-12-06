package WebPageScraper;

public class ProgramCore {
    private static FileHandler fileHandler;

    public ProgramCore() {
        fileHandler = new FileHandler();
    }

    public String parsePage(Integer pagesNumber, String articleType) {
        System.out.printf("Starting scraping %s pages, with '%s' type\n", pagesNumber, articleType);
        if(!CheckIsCorrectInputs(pagesNumber)) { return "Incorrect number"; }
        try {
            for (var article : new ParsePage(pagesNumber, articleType).parse()) {
                fileHandler.addPage(article);
            }
        }
        catch(Exceptions.InvalidRequest | Exceptions.NothingIsFound e) { return e.getMessage(); }
        return "Saved all articles";
    }

    private boolean CheckIsCorrectInputs(Integer number) {
        return number > 1;
    }
}
