package thankgame;

import java.util.Vector;

/**
 * 敌人坦克  多个电脑考虑多线程 Vector
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();


    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    // 将 MyPanel 的成员 Vector<EnemyTank> enemyTanks = new Vector<>();
    // 设置到 EnemyTank 的成员 enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    /**
     * @return true - 重叠或者碰撞  false - 未重叠或者碰撞
     */
    public boolean isTouchEnemyTank() {
        // 判断当前坦克的方向
        switch (this.getDirect()) {
            case 0: // 上
                // 与其他敌人坦克进行比较
                for(int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if(enemyTank != this) {
                        /**
                         * 敌人坦克方向   上下
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 40]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 60]
                         * 当前坦克的左右上角坐标与之进行比较
                         * 左上角坐标 [this.getX(), this.getY()]
                         * 右上角坐标 [this.getX() +40, this.getY()]
                         */
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 比较左上角坐标 [this.getX(), this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 比较右上角坐标 [this.getX() +40, this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /**
                         * 敌人坦克方向   左右
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 60]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 40]
                         * 当前坦克的左右上角坐标与之进行比较
                         * 左上角坐标 [this.getX(), this.getY()]
                         * 右上角坐标 [this.getX() +40, this.getY()]
                         */
                        if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 比较左上角坐标 [this.getX(), this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 比较右上角坐标 [this.getX() +40, this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: // 右
                // 与其他敌人坦克进行比较
                for(int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if(enemyTank != this) {
                        /**
                         * 敌人坦克方向   上下
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 40]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 60]
                         * 当前坦克的右上角和右下角坐标与之进行比较
                         * 右上角坐标 [this.getX() + 60, this.getY()]
                         * 右下角坐标 [this.getX() +60, this.getY() + 40]
                         */
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 比较右上角坐标 [this.getX() + 60, this.getY()]
                            if(this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 比较右下角坐标 [this.getX() +60, this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /**
                         * 敌人坦克方向   左右
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 60]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 40]
                         * 当前坦克的右上角右下角坐标与之进行比较
                         * 右上角坐标 [this.getX() + 60, this.getY()]
                         * 右下角坐标 [this.getX() +60, this.getY() + 40]
                         */
                        if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 比较右上角坐标 [this.getX() + 60, this.getY()]
                            if(this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 比较右下角坐标 [this.getX() +60, this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: // 下
                // 与其他敌人坦克进行比较
                for(int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if(enemyTank != this) {
                        /**
                         * 敌人坦克方向   上下
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 40]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 60]
                         * 当前坦克的左右下角坐标与之进行比较
                         * 左下角坐标 [this.getX(), this.getY() + 60]
                         * 右下角坐标 [this.getX() +40, this.getY() +60]
                         */
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 比较左下角坐标 [this.getX(), this.getY() + 60]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() +60 >= enemyTank.getY() && this.getY() +60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 比较右下角坐标 [this.getX() +40, this.getY() +60]
                            if(this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() +60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /**
                         * 敌人坦克方向   左右
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 60]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 40]
                         * 当前坦克的左右上角坐标与之进行比较
                         * 左下角坐标 [this.getX(), this.getY() + 60]
                         * 右下角坐标 [this.getX() +40, this.getY() +60]
                         */
                        if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 比较左上角坐标 [this.getX(), this.getY() + 60]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() +60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 比较右上角坐标 [this.getX() +40, this.getY() +60]
                            if(this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() +60 >= enemyTank.getY() && this.getY() + 60<= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: // 左
                // 与其他敌人坦克进行比较
                for(int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if(enemyTank != this) {
                        /**
                         * 敌人坦克方向   上下
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 40]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 60]
                         * 当前坦克的左上角和左下角坐标与之进行比较
                         * 左上角坐标 [this.getX(), this.getY()]
                         * 左下角坐标 [this.getX(), this.getY() + 40]
                         */
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 比较左上角坐标 [this.getX(), this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 比较左下角坐标 [this.getX(), this.getY() + 40]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        /**
                         * 敌人坦克方向   左右
                         * 定位坐标 确定坦克范围 x:[enemyTank.getX(),enemyTank.getX() + 60]
                         *                    y:[enemyTank.getY(),enemyTank.getY() + 40]
                         * 当前坦克的左右上角坐标与之进行比较
                         * 左上角坐标 [this.getX(), this.getY()]
                         * 左下角坐标 [this.getX(), this.getY() + 40]
                         */
                        if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 比较左上角坐标 [this.getX(), this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 比较右上角坐标 [this.getX(), this.getY() + 40]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false; // 未碰撞
    }

    @Override
    public void run() {
        while (true) {
            //判断坦克是否有效 设置子弹颗数
            if(isLive && shots.size() <= 10) {
                Shot s = null;
                //根据其方向 创建对应的子弹
                switch(getDirect()) {
                    case 0:
                        s = new Shot(getX(), getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX(), getY(),1);
                        break;
                    case 2:
                        s = new Shot(getX(), getY() ,2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY(),3);
                        break;
                }
                shots.add(s);
                // 启动
                new Thread(s).start();

            }
            // 根据坦克的方向 和 是否与其他坦克碰撞  以一定的距离 来继续移动
            switch (getDirect()) {
                case 0:
                    for(int i = 0; i < 30; i++) {
                        if(getY() >= 0 && !isTouchEnemyTank()) {
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
                        if(getX() + 60 < 1000 && !isTouchEnemyTank()) {
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
                        if(getY() + 60 < 750 && !isTouchEnemyTank()) {
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
                        if(getX() <= 0 && !isTouchEnemyTank()) {
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
            // 然后随机改变方向
            setDirect((int)(Math.random() * 4));
            if(!isLive) {
                break;
            }
        }
    }
}
