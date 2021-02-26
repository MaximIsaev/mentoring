package nio.task_2;

import java.io.File;
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
            System.out.println("Your function number is: ");
            int number = scanner.nextInt();
            System.out.println("Your path is: ");
            String path = scanner.next();


            while (!isValid(path))
                if (!isValid(path)) {
                    System.out.println("No such path");
                    break;
                }

            System.out.println("\n=================RESULT=================\n");
            File file = Paths.get(path).toFile();
            switch (number) {
                case 1:
                    System.out.println("File with the maximum number of letters ‘s’ in the name: ");
                    System.out.println(findFileWithMaxSLetters(file));
                    break;
                case 2:

                    List<File> files = getAllFiles(file).stream()
                            .sorted(Comparator.comparingLong(File::length).reversed())
                            .limit(5)
                            .collect(Collectors.toList());
                    System.out.println("Top-5 largest files: ");
                    files.forEach(f -> System.out.println(f.getName() + " - " + f.length() + " bytes"));
                    break;
                case 3:
                    System.out.println("The average file size is: " +
                            getAllFiles(file).stream()
                                    .mapToLong(File::length)
                                    .average()
                                    .orElse(Double.NaN)
                            + " bytes");
                    break;
                case 4:
                    printGroupFilesAndDirs(file);
                    break;
                default:
                    System.out.println("No such function");
                    break;
            }
            System.out.println();
            System.out.println();
            System.out.println();
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

    private static void printGroupFilesAndDirs(File dir) {
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

        for (Map.Entry<Character, Map<String, List<File>>> entry : collect.entrySet()) {
            int dirsAmount = entry.getValue().get("dirs") == null ? 0 : entry.getValue().get("dirs").size();
            int filesAmount = entry.getValue().get("files") == null ? 0 : entry.getValue().get("files").size();
            System.out.println(entry.getKey() + ": dirs " + dirsAmount + ", files: " + filesAmount);
        }
    }
}
