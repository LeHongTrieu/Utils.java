import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    /**
     * Hàm xóa file.
     */
    public static void clearFileContent(String path) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hàm đọc file.
     */
    public static String readContentFromFile(String path) {
        StringBuilder s = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = read.readLine()) != null) {
                s.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    /**
     * Hàm ghi file.
     */
    public static void writeContentToFile(String path, String content) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter(path, true));
            boolean check = true;
            for (String s : lines) {
                if (content.equals(s)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                write.write(content);
            } else {
                clearFileContent(path);
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).equals(content)) {
                        lines.remove(i);
                        i--;
                    }
                }
                lines.add(content);
                write.write(lines.get(0));
                for (int i = 1; i < lines.size(); i++) {
                    write.write("\n");
                    write.write(lines.get(i));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (write != null) {
                    write.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hàm ghi file.
     */
    public static void appendContentToFile(String path, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path, true));
            writer.write("\n");
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Error appending content to file", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hàm tìm file.
     */
    public static File findFileByName(String folderPath, String fileName) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    return file;
                }
            }
        }
        return null;
    }
    
    /**
     * Test chương trình.
     */
//    public static void main(String []args) {
//        Utils ut = new Utils();
//        String path = "D:\\Admin\\Desktop\\OOP_Java\\Tuan13\\src\\test.txt";
//        appendContentToFile(path, "Hi word");
//        writeContentToFile(path, "Hello word");
//    }
}

