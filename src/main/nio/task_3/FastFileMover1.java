package nio.task_3;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;

public class FastFileMover1 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Need 2 arguments");
            System.exit(0);
        }
        String from = args[0];
        String to = args[1];

        Instant start = Instant.now();

        simpleFileTransfer(from, to);
//        bufferedFileTransfer(from, to);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Coping file with 10GB size takes " + duration.getSeconds() + " second");
    }

    private static void simpleFileTransfer(String from, String to) {
        try {
            Path oldFile = Paths.get(from);
            Path newDestination = Paths.get(to);
            if (!Files.exists(oldFile) || Files.isDirectory(oldFile)) {
                System.out.println("File does not exist or a directory");
                System.exit(0);
            }
            String newFileFullPath = newDestination.toAbsolutePath().toString() + File.separator + oldFile.toFile().getName();
            Files.copy(oldFile, Files.createFile(Paths.get(newFileFullPath)), StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(oldFile);
        } catch (InvalidPathException e) {
            System.out.println("The path is incorrect");
            e.printStackTrace();
            System.exit(0);
        } catch (FileAlreadyExistsException e) {
            System.out.println("The file is already existing");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Sorry, something went wrong");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void bufferedFileTransfer(String from, String to) {
        try {
            Path oldFile = Paths.get(from);
            Path newDestination = Paths.get(to);
            if (!Files.exists(oldFile) || Files.isDirectory(oldFile)) {
                System.out.println("File does not exist or a directory");
                System.exit(0);
            }
            String newFileFullPath = newDestination.toAbsolutePath().toString() + File.separator + oldFile.toFile().getName();

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldFile.toFile()), 100000);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFileFullPath), 100000);
            int read = bis.read();
            while (read != -1) {
                char c = (char) read;
                bos.write(c);
                read = bis.read();
            }

            bos.flush();

            bos.close();
            bis.close();

//            File file = new File(newFileFullPath);
//            file.createNewFile();


//            Files.copy(oldFile, Files.createFile(Paths.get(newFileFullPath)), StandardCopyOption.REPLACE_EXISTING);
//            Files.deleteIfExists(oldFile);
        } catch (InvalidPathException e) {
            System.out.println("The path is incorrect");
            e.printStackTrace();
            System.exit(0);
        } catch (FileAlreadyExistsException e) {
            System.out.println("The file is already existing");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Sorry, something went wrong");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
