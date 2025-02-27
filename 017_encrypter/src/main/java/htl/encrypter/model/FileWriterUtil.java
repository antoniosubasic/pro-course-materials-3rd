package htl.encrypter.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtil {
    private static final String DIRECTORY = "files/";

    public static void writeToFile(String filename, String content) {
        try {
            var dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            var file = new File(DIRECTORY + filename);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
