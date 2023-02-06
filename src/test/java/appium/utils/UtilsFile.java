package appium.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class UtilsFile {

    private static final Logger logger = Logger.getLogger(String.valueOf(UtilsFile.class));

    public static void writeToFile(String filePath, String text) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFile(String filePath) {
        File file = new File(filePath);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            logger.info("File '" + filePath + "' not found");
        }
    }

    public static String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
    }

    public static void updateFile(String filePath, String text) {
        removeFile(filePath);
        writeToFile(filePath, text);
    }
}
