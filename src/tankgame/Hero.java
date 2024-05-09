package tankgame;

import java.util.Vector;

public class Hero extends Tank{
    // 定义一个 Shot 对象,表示一个射击（线程）
    Shot shot = null;

    // 发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void shotEnemyTank() {
        //控制子弹的颗数
        if (shots.size() == 5) {
            return;
        }
        // 根据当前 Hero 的位置和方向 创建 Shot 对象
        switch (getDirect()) { // 坦克的方向  0--上  1--右 2--下 3--左
            case 0:
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() + 20,1);
                break;
            case 2:
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY() + 20,3);
                break;
        }

        // 把新建的 子弹 放在shots
        shots.add(shot);

        // 启动射击线程
        Thread thread = new Thread(shot);
        thread.start();
    }
}
