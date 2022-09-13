package fgen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class FGen {
    String string = "./";

    public FGen(final String inputPath)
    {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(inputPath))
        {
            String file = new String(stream.readAllBytes());
            var all = file.split("\n");
            for (var line : all)
            {
                char first = line.charAt(0);
                String subsequence = line.substring(2);
                switch (first) {
                    case '+' -> create(string + subsequence);
                    case '>' -> change(subsequence);
                    case '-' -> delete(string + subsequence);
                    default -> throw new RuntimeException();
                }
            }
        }
        catch (final IOException | RuntimeException e){
            throw new RuntimeException();
        }
    }

    private void create(String token)
    {
        File file = new File(token);
        if(token.matches(".*/")) {
            file.mkdirs();
            return;
        }
        int position = token.lastIndexOf('/');
        String dirs = token.substring(0, position);
        File tmp = new File(dirs);
        tmp.mkdirs();
        try {
            file.createNewFile();
        } catch (final IOException e) {
        }
    }

    private static boolean deleteDirectory(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirectory(f);
            }
        }
        return file.delete();
    }

    private void delete(String str)
    {
        try {
            Paths.get(str);
            deleteDirectory(new File(str));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void change(String token)
    {
        File verif = new File(string, token);
        if(verif.exists() && verif.isDirectory()) {
            string += token;
            if(token.charAt(token.length()-1) != '/')
                string += '/';
        }
        else
            throw new RuntimeException();
    }
}