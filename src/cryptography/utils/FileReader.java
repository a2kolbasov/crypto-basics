package cryptography.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class FileReader {
    public static String readAll(String fileName) throws IOException {
        return Files.readString(Paths.get(fileName));
    }
}
