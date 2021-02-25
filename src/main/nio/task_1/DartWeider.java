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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaskColor() {
        return maskColor;
    }

    public void setMaskColor(String maskColor) {
        this.maskColor = maskColor;
    }

    public String getFavoriteSwordColor() {
        return favoriteSwordColor;
    }

    public void setFavoriteSwordColor(String favoriteSwordColor) {
        this.favoriteSwordColor = favoriteSwordColor;
    }

    public String getFavoriteMask() {
        return favoriteMask;
    }

    public void setFavoriteMask(String favoriteMask) {
        this.favoriteMask = favoriteMask;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getSwordLength() {
        return swordLength;
    }

    public void setSwordLength(int swordLength) {
        this.swordLength = swordLength;
    }

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
