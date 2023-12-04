package WebPageScraper;

public class Article {
    public String title;
    public String content;
    public Integer pageNumber;

    public Article(String articleTitle, String articleContent, Integer pageNum) {
        title = formatTitle(articleTitle);
        content = articleContent;
        pageNumber = pageNum;
    }

    private static String formatTitle(String title) {
        return title.replaceAll("[\\s\\p{Punct}]+", "_") + ".txt";
    }
}
