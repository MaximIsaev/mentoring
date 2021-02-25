package nio.task_1;

import java.io.Serializable;

public class Car implements Serializable {

    public Car(int id, String name, String model, String color) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.color = color;
    }

    private transient int id;
    private String name;
    private String model;
    private String color;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
