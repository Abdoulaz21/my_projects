package myCat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class MyCat {

    abstract protected String catLine(String line, int number);

    private String catFile(String file) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            var s = (ArrayList<String>)Files.readAllLines(Paths.get(file));
            for (int i = 0; i < s.size(); i++) {
                stringBuilder.append(catLine(s.get(i), i + 1));
                if (i < s.size() - 1) {
                    stringBuilder.append("\n");
                }
                else if (content.charAt(content.length() - 1) == '\n') {
                    stringBuilder.append("\n");
                }
            }
        } catch(IOException e) {
            return "";
        }
        return stringBuilder.toString();
    }

    protected String cat(String... files) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var file: files) {
            stringBuilder.append(catFile(file));
        }
        return stringBuilder.toString();
    }
}
