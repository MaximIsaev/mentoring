package nio.task_1;

import java.io.Serializable;
import java.util.List;

public class DeathStar implements Serializable {

    public DeathStar() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanetsDestroyed() {
        return planetsDestroyed;
    }

    public void setPlanetsDestroyed(int planetsDestroyed) {
        this.planetsDestroyed = planetsDestroyed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public DartWeider getWeider() {
        return weider;
    }

    public void setWeider(DartWeider weider) {
        this.weider = weider;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

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
