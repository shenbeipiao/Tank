package thankgame;

/**
 * 坦克子弹类
 */

public class Shot extends Thread{
    // 子弹的横、纵坐标  即炮筒终点的坐标
    int x;
    int y;
    int direct = 0; // 子弹的方向
    int speed = 4; // 子弹的速度
    boolean isLive = true; //子弹是否还在游戏页面内

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() { //射击发射子弹
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            // System.out.println("子弹移动了");
            //判断子弹位置，是否销毁
            if(!(x >= 0 && x <= 1000 && y >=0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
