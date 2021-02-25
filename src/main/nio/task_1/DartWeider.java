package nio.task_1;

import java.io.Serializable;

public class DartWeider implements Serializable {

    public DartWeider(int id, int swordLength, String maskColor, String favoriteSwordColor, String favoriteMask) {
        this.id = id;
        this.swordLength = swordLength;
        this.maskColor = maskColor;
        this.favoriteSwordColor = favoriteSwordColor;
        this.favoriteMask = favoriteMask;
    }

    private transient int id;
    private transient int swordLength;
    private String maskColor;
    private String favoriteSwordColor;
    private String favoriteMask;
    private String slogan = "Luuuuk, ya tvoy papka";

    @Override
    public String toString() {
        return "DartWeider{" +
                "id=" + id +
                ", swordLength=" + swordLength +
                ", maskColor='" + maskColor + '\'' +
                ", favoriteSwordColor='" + favoriteSwordColor + '\'' +
                ", favoriteMask='" + favoriteMask + '\'' +
                ", slogan='" + slogan + '\'' +
                '}';
    }
}
