package thankgame;

import java.util.Vector;

/**
 * 敌人坦克  多个电脑考虑多线程 Vector
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }


    @Override
    public void run() {
        while (true) {
            // 根据坦克的方向 以一定的距离 来继续移动
            switch (getDirect()) {
                case 0:
                    for(int i = 0; i < 30; i++) {
                        if(getY() >= 0) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for(int i = 0; i < 30; i++) {
                        if(getX() + 60 < 1000) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for(int i = 0; i < 30; i++) {
                        if(getY() + 60 < 750) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for(int i = 0; i < 30; i++) {
                        if(getX() <= 0) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 然后随机的改变方向
            setDirect((int)(Math.random() * 4));
            if(!isLive) {
                break;
            }
        }
    }
}
