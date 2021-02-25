package nio.task_1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Serialization {

    private static final String DEATH_STAR_FILE_PATH = "deathstar.txt";

    public static void main(String[] args) throws InterruptedException {

        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1, "Car 1", "Model 1", "Color 1"));
        carList.add(new Car(2, "Car 2", "Model 2", "Color 2"));
        carList.add(new Car(3, "Car 3", "Model 3", "Color 3"));

        DeathStar deathStar = new DeathStar(
                1,
                666,
                "A new death star",
                "Model number 5",
                new DartWeider(1, 4, "Black", "Red", "Angry mask"),
                carList
        );

        saveDeathStar(deathStar);

        System.out.println("Wait, wait wait wait......");
        Thread.sleep(3000);

        System.out.println("Reading file with the secret blueprints");
        Thread.sleep(4000);
        readDeathStar();

        System.out.println("Deleting the trash file. Say 'Bye Bye!'.....");
        Thread.sleep(3000);
        deleteTempFiles();
    }

    private static void saveDeathStar(DeathStar star) {
        if (star == null)
            return;

        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(DEATH_STAR_FILE_PATH))) {
            ous.writeObject(star);
            System.out.println("Death star recorded successfully: " + star.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Death star does not want to go to file, because the file is not exists");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Death star does not want to go to file cause of another IO exception as usual");
            e.printStackTrace();
        }
    }

    private static void readDeathStar() {
        try (ObjectInputStream ous = new ObjectInputStream(new FileInputStream(DEATH_STAR_FILE_PATH))) {
            DeathStar deathStar = (DeathStar) ous.readObject();
            System.out.println("Death star read successfully: " + deathStar.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Ooops, file now found....again");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Well well well, another IO exception....your fault...");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Ok, I don't know what to do here, it's your problem, I'm going home ---->>>");
            e.printStackTrace();
        }
    }

    private static void deleteTempFiles() {
        Path path = Paths.get(DEATH_STAR_FILE_PATH);
        //no IO catch, this is not a real service...
        try {
            if (Files.deleteIfExists(path)) {
                System.out.println("File with Death Start blueprints deleted successfully. Aloha!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Well, you have a problem here. I think the file is not exists...maybe. And maybe not....");
        }
    }
}
