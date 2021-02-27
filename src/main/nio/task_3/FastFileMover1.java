package nio.task_3;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
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

        Path file = Paths.get(from);
        Path newDestination = Paths.get(to);
        validatePath(file);

        long totalNanos = 0;
        int iterations = 1000;
        String reportFileName = file.getFileName() + "_bufferedFileTransfer.txt";
        for (int i = 0; i < iterations; i++) {
            Instant start = Instant.now();

//            simpleFileTransfer(file, newDestination);
        bufferedFileTransfer(file, newDestination);
//        fileChannelFileTransfer(file, newDestination);

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            String result;
            result = "Iteration " + i + ": Coping file " + file + " to " + newDestination + " takes " + duration.getNano() / 1000000 + " ms\n";
            System.out.print(result);
            createReport(reportFileName, result);

            String newDestinationFullPath = getNewDestinationFullPath(file, newDestination);
            Path temp = file;
            file = Paths.get(newDestinationFullPath);
            newDestination = temp.getParent();

            totalNanos += duration.getNano();

        }

        long avg = totalNanos / iterations;
        createReport(reportFileName, "Average ms of " + iterations + " iterations for coping file: " + avg / 1000000 + " ms");


    }

    private static void createReport(String fileName, String report) {
        try {
            Path file = Paths.get(fileName);
            if (Files.exists(file)) {
                Files.write(file, report.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            } else {
                Files.write(file, report.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validatePath(Path oldFile) {
        if (!Files.exists(oldFile) || Files.isDirectory(oldFile)) {
            System.out.println("File does not exist or a directory");
            System.exit(0);
        }
    }

    private static void fileChannelFileTransfer(Path file, Path newDestination) {

        try (RandomAccessFile read = new RandomAccessFile(file.toFile(), "r");
             RandomAccessFile write = new RandomAccessFile(getNewDestinationFullPath(file, newDestination), "rw");
             FileChannel readChannel = read.getChannel();
             FileChannel writeChannel = write.getChannel()
        ) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.clear();
            while (readChannel.read(buf) > 0) {
                buf.flip();
                writeChannel.write(buf);
                buf.clear();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: ");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Sorry, something went wrong");
            e.printStackTrace();
            System.exit(0);
        }

    }

    private static void simpleFileTransfer(Path file, Path newDestination) {
        try {
            Files.copy(file,
                    Files.createFile(Paths.get(getNewDestinationFullPath(file, newDestination))),
                    StandardCopyOption.REPLACE_EXISTING
            );
            Files.deleteIfExists(file);
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

    private static void bufferedFileTransfer(Path file, Path newDestination) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.toFile()), 100000);
             BufferedOutputStream bos = new BufferedOutputStream(
                     new FileOutputStream(getNewDestinationFullPath(file, newDestination)), 100000)) {

            int read = bis.read();
            while (read != -1) {
                char c = (char) read;
                bos.write(c);
                read = bis.read();
            }
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.out.println("Sorry, something went wrong");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static String getNewDestinationFullPath(Path file, Path newDestination) {
        return newDestination.toAbsolutePath().toString() + File.separator + file.toFile().getName();
    }
}
