import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileManager {

    // Write text to file, overwrite if exists
    public static void saveToFile(File file, String content) throws Exception {
        Files.writeString(file.toPath(), content,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Reads all text from file into String
    public static String readFromFile(File file) throws Exception {
        return Files.readString(file.toPath());
    }
}