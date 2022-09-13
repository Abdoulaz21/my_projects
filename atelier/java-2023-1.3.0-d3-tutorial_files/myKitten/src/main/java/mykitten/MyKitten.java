package mykitten;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Stream;

public class MyKitten {
    /**
     * Initializer.
     *
     * @param srcPath Source file path.
     */
    public MyKitten(String srcPath) {
        throw new NoClassDefFoundError();
    }

    /**
     * Use the streamContent to replace `wordToReplace` by "miaou". Don't forget
     * to add the line number before hand for each line. Store the new result
     * directly in the streamContent field.
     *
     * @param wordToReplace The word to replace
     */
    public void replaceByMiaou(String wordToReplace) {
        throw new NoClassDefFoundError();
    }

    /**
     * Use the streamContent to write the content into the destination file.
     *
     * @param destPath Destination file path.
     */
    public void toFile(String destPath) {
        throw new NoClassDefFoundError();
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
        try(BufferedReader mybr = new BufferedReader(new FileReader(srcPath));
            BufferedWriter mybw = new BufferedWriter(new FileWriter(destPath))){
            String line;
            int index = 1;
            while ((line = mybr.readLine()) != null){
                line = line.replaceAll(wordToReplace, "miaou");
                String toprint = index + " " + line;
                mybw.write(toprint);
                index++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Stream<String> streamContent;
}
