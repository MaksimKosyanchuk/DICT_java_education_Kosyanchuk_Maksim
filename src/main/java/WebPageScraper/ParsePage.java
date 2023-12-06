package WebPageScraper;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParsePage {
    private final Integer pagesNumber;
    private final String url;
    private final String articleType;

    public ParsePage(Integer number, String type) {
        pagesNumber = number;
        url = String.format("https://www.nature.com/nature/articles?sort=PubDate&amp;year=2022&amp;page=%s", number);
        articleType = type;
    }

    public ArrayList<Article> parse() throws Exceptions.InvalidRequest, Exceptions.NothingIsFound {
        ArrayList<Article> articles = new ArrayList<>();
        Elements elements;
        try {
            var document = Jsoup.connect(url).get();
            elements = document.select(".u-full-height.c-card.c-card--flush");
            for(var element : elements) {
                String link = "https://www.nature.com" + element.select("a").attr("href");
                String title = element.select("a").text().replace(" ", "_");
                String type = element.select(".c-meta__type").text();
                if (!type.startsWith(articleType)) {
                    continue;
                }
                System.out.println("Found article with '" + title + "' title");
                articles.add(new Article(title, getContent(link), pagesNumber));
            }
        }
        catch(java.io.IOException e) { throw new Exceptions.InvalidRequest(url); }
        if(articles.isEmpty()) { throw new Exceptions.NothingIsFound(); }
        return articles;
    }

    private String getContent(String link) throws java.io.IOException {
        var sb = new StringBuilder();
        for(var element : Jsoup.connect(link).get().select(".c-article-body.main-content")) {
            sb.append(element.text());
        }
        return sb.toString();
    }
}
