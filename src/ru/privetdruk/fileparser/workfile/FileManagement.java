package ru.privetdruk.fileparser.workfile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class FileManagement {
    private FileManagement() {
    }

    public static String convertFileToString(String fileName) throws IOException {
        StringBuilder textFile = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null)
                textFile.append(line.replaceAll("\t", ""));
        }

        return textFile.toString();
    }

    public static void saveListToFile(String fileName, List<String> list) throws IOException {
        try (PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (String line : list)
                bw.println(line);
        }
    }
}
