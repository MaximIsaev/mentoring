package nio.task_1;

import java.io.Serializable;
import java.util.List;

public class DeathStar implements Serializable {

    public DeathStar(int id, int planetsDestroyed, String name, String model, DartWeider weider, List<Car> car) {
        this.id = id;
        this.planetsDestroyed = planetsDestroyed;
        this.name = name;
        this.model = model;
        this.weider = weider;
        this.car = car;
    }

    private transient int id;
    private transient int planetsDestroyed;
    private String name;
    private String model;
    private DartWeider weider;
    private List<Car> car;

    @Override
    public String toString() {
        return "DeathStar{" +
                "id=" + id +
                ", planetsDestroyed=" + planetsDestroyed +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", weider=" + weider +
                ", car=" + car +
                '}';
    }
}
