package WebPageScraper;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    public FileHandler() {
        checkIsDirectoryExists(Paths.get("src/main/java/WebPageScraper/Pages"));
    }

    private static void checkIsDirectoryExists(Path path) {
        if(Files.notExists(path)) { createDirectory(path); }
    }

    private static void createDirectory(Path path) {
        try{
            Files.createDirectory(path);
        }
        catch(java.io.IOException e) { System.out.println(e.getMessage()); }
    }

    private static void addFile(String path, String content) {
        try(var writer = new FileWriter(path, false)) {
            writer.write(content);
            writer.flush();
        }
        catch(java.io.IOException e) { System.out.println(e.getMessage()); }
    }

    public void addPage(Article article) {
        System.out.printf("Adding new article to files with '%s' title%n", article.title);
        var directory = String.format("src/main/java/WebPageScraper/Pages/Page_%s/", article.pageNumber);
        checkIsDirectoryExists(Paths.get(directory));
        var path = String.format(directory + String.format("/%s", article.title));
        addFile(path, article.content);
    }
}
