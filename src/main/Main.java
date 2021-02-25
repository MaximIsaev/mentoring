package main;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        File dir = new File("C:\\");
        long totalFiles = listCatalogsRecursively(dir);
        System.out.println("Total files: " + totalFiles);
    }

    private static long listCatalogsRecursively(File dir) {
        long total = 0;
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                total += files.length;
                for (File file : files) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        total += listCatalogsRecursively(file);
                    }
                }
            }
        }
        return total;
    }
}
