package thankgame;

public class Tank {
    /**
     * role 坦克
     * version 1.0
     */

    private int x; //坦克的横坐标
    private int y; //坦克的纵坐标
    private int direct; // 坦克的方向 0--上--W  1--右--D 2--下--S 3--左--A

    // 坦克移动方法
    public void moveUp() { //上
        y -= 20;
    }
    public void moveDown() { //下
        y += 20;
    }
    public void moveLeft() { //左
        x -= 20;
    }
    public void moveRight() { //右
        x += 20;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

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
