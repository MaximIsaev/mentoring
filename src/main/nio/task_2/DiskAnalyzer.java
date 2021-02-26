package nio.task_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DiskAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Disk analyzer utilities greeting you!");
            System.out.println("Please, enter a function number and a folders path via comma: ");
            System.out.println(
                    "1. Search for the file name with the maximum number of letters ‘s’ in the name, display the path to it.\n" +
                            "2. Print Top-5 largest files (by size in bytes).\n" +
                            "3. The average file size in the specified directory or any its subdirectory.\n" +
                            "4. The number of files and folders, divided by the first letters of the alphabet (for example, 100,000 files and 200 folders begin with the letter A)."
            );

            int number;
            System.out.println("Enter a function number: ");
            while (!scanner.hasNext("[1-4]")) {
                System.out.println("No such function");
                scanner.nextInt();
            }
            number = scanner.nextInt();

            String path;
            do {
                System.out.println("Enter a path: ");
                path = scanner.next();
                if (!isValid(path)) {
                    System.out.println("The path is not valid");
                }

            } while (!isValid(path));

            System.out.println("\n=================RESULT=================\n");
            File file = Paths.get(path).toFile();
            switch (number) {
                case 1:
                    String result = "File with the maximum number of letters ‘s’ in the name: " + findFileWithMaxSLetters(file);
                    System.out.println(result);
                    writeToFile("task_1.txt", result);
                    break;
                case 2:
                    StringBuilder result2 = new StringBuilder("Top-5 largest files: ");
                    List<File> files = getAllFiles(file).stream()
                            .sorted(Comparator.comparingLong(File::length).reversed())
                            .limit(5)
                            .collect(Collectors.toList());
                    files.forEach(f -> result2.append(f.getName()).append(" - ").append(f.length()).append(" bytes\n"));
                    writeToFile("task_2.txt", result2.toString());
                    System.out.println(result2);
                    break;
                case 3:
                    String result3 = "Average file size in the specified directory: " +
                            getAllFiles(file).stream()
                                    .mapToLong(File::length)
                                    .average()
                                    .orElse(Double.NaN)
                            + " bytes";
                    writeToFile("task_3.txt", result3);
                    System.out.println(result3);
                    break;
                case 4:
                    String result4 = printGroupFilesAndDirs(file);
                    writeToFile("task_4.txt", result4);
                    System.out.println(result4);
                    break;
            }
            System.out.print("\n\n\n");
        }
    }

    private static Boolean isValid(String filePath) throws IllegalArgumentException {
        return filePath != null && !filePath.trim().equals("") && Files.exists(Paths.get(filePath));
    }

    private static File findFileWithMaxSLetters(File path) {
        if (path != null && path.isFile()) {
            return path;
        }
        return findFileWithMaxSLettersRecursively(path);
    }

    private static File findFileWithMaxSLettersRecursively(File path) {
        File maxFile = null;
        if (path != null && path.exists() && path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (maxFile == null || countSOccurrence(maxFile.getName()) < countSOccurrence(file.getName())) {
                        maxFile = file;
                    }
                    if (file.isDirectory()) {
                        File file1 = findFileWithMaxSLettersRecursively(file);
                        if (file1 != null && countSOccurrence(file1.getName()) > countSOccurrence(maxFile.getName())) {
                            maxFile = file1;
                        }
                    }
                }
            }
        }
        return maxFile;
    }

    private static long countSOccurrence(String word) {
        return word.chars().filter(ch -> ch == 's' || ch == 'S').count();
    }

    private static List<File> getAllFiles(File dir) {
        List<File> fileList = new ArrayList();
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(file);
                    if (file.isDirectory()) {
                        fileList.addAll(getAllFiles(file));
                    }
                }
            }
        }
        return fileList;
    }

    private static String printGroupFilesAndDirs(File dir) {
        List<File> files = getAllFiles(dir);
        Map<Character, Map<String, List<File>>> collect = files.stream()
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.groupingBy(file -> file.getName().charAt(0), LinkedHashMap::new, Collectors.groupingBy(item -> {
                    if (item.isDirectory()) {
                        return "dirs";
                    } else {
                        return "files";
                    }
                })));

        StringBuilder str = new StringBuilder();
        for (Map.Entry<Character, Map<String, List<File>>> entry : collect.entrySet()) {
            int dirsAmount = entry.getValue().get("dirs") == null ? 0 : entry.getValue().get("dirs").size();
            int filesAmount = entry.getValue().get("files") == null ? 0 : entry.getValue().get("files").size();
            str.append(entry.getKey()).append(": dirs ").append(dirsAmount).append(", files: ").append(filesAmount).append("\n");
        }
        return str.toString();
    }

    private static void writeToFile(String fileName, String result) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
