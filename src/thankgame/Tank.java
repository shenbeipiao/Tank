package thankgame;

public class Tank {
    /**
     * role 电脑坦克
     * version 1.0
     */

    private int x; //坦克的横坐标
    private int y; //坦克的纵坐标

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
