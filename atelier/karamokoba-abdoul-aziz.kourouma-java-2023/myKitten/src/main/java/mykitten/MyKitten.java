package mykitten;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MyKitten {
    /**
     * Initializer.
     *
     * @param srcPath Source file path.
     */
    public MyKitten(String srcPath) {
        try {
            streamContent = Files.lines(Paths.get(srcPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use the streamContent to replace `wordToReplace` by "miaou". Don't forget
     * to add the line number before hand for each line. Store the new result
     * directly in the streamContent field.
     *
     * @param wordToReplace The word to replace
     */
    public void replaceByMiaou(String wordToReplace) {
        AtomicInteger indexLine = new AtomicInteger(1);
        streamContent = streamContent.map(line -> indexLine.getAndIncrement() + " " + line.replaceAll(wordToReplace, "miaou"));
    }

    /**
     * Use the streamContent to write the content into the destination file.
     *
     * @param destPath Destination file path.
     */
    public void toFile(String destPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destPath))) {
            streamContent.forEach(line -> {
                try {
                    bw.write(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an instance of MyKitten and calls the above methods to do it
     * straightforward.
     *
     * @param srcPath       Source file path
     * @param destPath      Destination file path
     * @param wordToReplace Word to replace
     */
    public static void miaou(String srcPath, String destPath,
                             String wordToReplace) {
        MyKitten kitten = new MyKitten(srcPath);
        kitten.replaceByMiaou(wordToReplace);
        kitten.toFile(destPath);
    }

    public Stream<String> streamContent;
}
