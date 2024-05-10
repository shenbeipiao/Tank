package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;

/**
 * version 1.0
 * 坦克大战的绘图区域
 */
@SuppressWarnings("all")
public class MyPanel extends JPanel implements KeyListener , MouseListener, Runnable {
    // 定义玩家坦克
    Hero hero = null;
    // 电脑坦克 放入 Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTanksSize = 3;
    // 定义 一个存放 Node 对象的 Vector 用于恢复上局游戏
    Vector<Node> nodes = new Vector<>();
    //定义一个 Vector 用于存放炸弹
    Vector<Bomb> bombs = new Vector<>();

    // 定义三张图片 用于显示爆炸效果
    Image image = Toolkit.getDefaultToolkit().getImage("TankGame/pages/tank.png");
    Image image1 = Toolkit.getDefaultToolkit().getImage("TankGame/pages/tank1.png");
    Image image2 = Toolkit.getDefaultToolkit().getImage("TankGame/pages/tank2.png");

    public MyPanel(String key) {
        File file =new File(Recorder.getRecordFile());
        if(file.exists()) {
            nodes=Recorder.getNodeAndEnemyTankRec();
        }else {
            System.out.println("只能开启新游戏");
            key="1";
        }
        //记录文件获取敌人坦克
        Recorder.setEnemyTanks(enemyTanks);

        // 初始化玩家坦克
        hero = new Hero(400,400);
        hero.setDirect(2);

       switch (key) {
           // 新游戏
           case "1" :
               //初始化电脑坦克
               for(int i = 0; i < enemyTanksSize; i++) {
                   EnemyTank enemyTank = new EnemyTank(100 * (1 + i), 0);

                   // 创建坦克
                   enemyTank.setEnemyTanks(enemyTanks);
                   // 设置初始方向
                   enemyTank.setDirect(2);
                   // 启动
                   new Thread(enemyTank).start();
                   //给电脑坦克加子弹
                   Shot shot = new Shot(enemyTank.getX(),enemyTank.getY(),enemyTank.getDirect());
                   enemyTank.shots.add(shot);

                   new Thread(shot).start();
                   enemyTanks.add(enemyTank);

               }
               break;
           // 上局游戏
           case "2" :
               //初始化电脑坦克
               for(int i = 0; i < nodes.size(); i++) {
                   Node node = nodes.get(i);
                   EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                   // 创建坦克
                   enemyTank.setEnemyTanks(enemyTanks);
                   // 设置初始方向
                   enemyTank.setDirect(node.getDirect());
                   // 启动
                   new Thread(enemyTank).start();
                   //给电脑坦克加子弹 加了第一颗子弹无法移动
                   // Shot shot = new Shot(enemyTank.getX(),enemyTank.getY(),enemyTank.getDirect());
                   // enemyTank.shots.add(shot);
                   //
                   // new Thread(shot).start();
                   enemyTanks.add(enemyTank);

               }
               break;
           default:
               System.out.println("输入有误");
       }
        // 音乐
        new AePlayWave("TankGame/pages/Tank01.wav").start();
    }

    public void showInfo(Graphics g) {
        // 画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);

        g.drawString("您累计击败的敌方坦克",1020,30);
        drawTank(1060,50,g,0,0);
        // 重置画笔颜色
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "",1200,80);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // 调用父类方法完成初始化
        g.fillRect(0,0,1000,850);// 填充矩形，默认黑色

        showInfo(g);
        // 画出玩家坦克 将其封装为单独的方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g,hero.getDirect(),1);
        }
        // 子弹 单颗
        // if(hero.shot != null && hero.shot.isLive == true) {
        //     g.draw3DRect(hero.shot.x,hero.shot.y,4,4,false);
        // }

        // 多颗子弹 遍历shots
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive == true) {
                g.draw3DRect(shot.x,shot.y,4,4,false);
            } else {
                // 该shot对象无效 则从 shots 中 移除
                hero.shots.remove(shot);
            }
        }

        // 第一个爆炸效果不显示 控制显示
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 如果 bombs 集合中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.life > 6) {
                g.drawImage(image,bomb.x,bomb.y,60,60,this);
            } else if(bomb.life > 3) {
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            } else {
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }

            bomb.lifeDown();

            if(bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        // 画出电脑坦克 遍历Vector
        for(int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive) { // 坦克存活
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);

                //Vector<Shot> shots = enemyTank.getShots();
                // 画出敌方坦克子弹
                for(int j = 0; j < enemyTank.shots.size(); j++) {
                    // 取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    // 绘制子弹
                    if (shot != null) {
                        if(shot.isLive) {
                            g.draw3DRect(shot.x + 18, shot.y + 18, 4,4,false);
                        } else { //移除子弹
                            enemyTank.shots.remove(shot);
                        }
                    }
                }
            } else {
                enemyTanks.remove(enemyTank);
            }
        }


    }

    // 画出坦克
    /**
     *
     * @param x 坦克左上角的 x 坐标
     * @param y 坦克左上角的 y 坐标
     * @param g 画笔
     * @param direct 坦克的方向（上下左右）
     * @param type 坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //根据坦克类型确定坦克颜色
        switch (type) {
            case 0: //玩家坦克 青色
                g.setColor(Color.cyan);
                break;
            case 1: //电脑坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向确定坦克方向 坦克的方向 0--上--W  1--右--D 2--下--S 3--左--A
        switch (direct) {
            case 0: // 向上
                g.fill3DRect(x, y, 10, 60, false);//坦克左边部分
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边部分
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间部分
                g.fillOval(x + 10,y + 20, 20, 20);//圆形部分
                g.drawLine(x + 20, y + 30, x + 20, y);//炮筒部分
                break;
            case 1: // 向右
                g.fill3DRect(x, y, 60, 10, false);//坦克左边部分
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克右边部分
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克中间部分
                g.fillOval(x + 20,y + 10, 20, 20);//圆形部分
                g.drawLine(x + 30, y + 20, x + 60, y+20);//炮筒部分
                break;
            case 2: // 向下
                g.fill3DRect(x, y, 10, 60, false);//坦克左边部分
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边部分
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间部分
                g.fillOval(x + 10,y + 20, 20, 20);//圆形部分
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮筒部分
                break;
            case 3: // 向左
                g.fill3DRect(x, y, 60, 10, false);//坦克左边部分
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克右边部分
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克中间部分
                g.fillOval(x + 20,y + 10, 20, 20);//圆形部分
                g.drawLine(x + 30, y + 20, x, y + 20);//炮筒部分
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    // 判断我方坦克子弹是否击中敌方坦克

    /**
     *
     * @param s 子弹
     * @param enemyTank 坦克
     */
    public void hitTank(Shot s, Tank enemyTank) throws InterruptedException {
        switch (enemyTank.getDirect()) { //子弹方向
            case 0: // 上下
            case 2:
              if(s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                  s.isLive = false;
                  enemyTank.isLive = false;

                  // 判断坦克类型 玩家还是敌方坦克
                  if (enemyTank instanceof EnemyTank) {
                    // 击败坦克数加一
                    enemyTanks.remove(enemyTank);
                    Recorder.addAllEnemyTankNum();
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                  } else { // 我方坦克被击中 退出游戏
                      Recorder.keepRecord();
                      Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                      bombs.add(bomb);
                      Thread.sleep(1000);
                      System.exit(0);
                  }
              }
              break;
            case 1: // 左右
            case 3  :
                if(s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;

                    // 判断坦克类型 玩家还是敌方坦克
                    if (enemyTank instanceof EnemyTank) {
                        // 击败坦克数加一
                        enemyTanks.remove(enemyTank);
                        Recorder.addAllEnemyTankNum();
                        Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                        bombs.add(bomb);
                    } else { // 我方坦克被击中 退出游戏
                        Recorder.keepRecord();
                        Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                        bombs.add(bomb);
                        Thread.sleep(1000);
                        System.exit(0);
                    }
                }
                break;
        }
    }

    // 判断敌方坦克子弹是否击中玩家坦克
    public void hitHero() throws InterruptedException {
        // 遍历敌人坦克
        if(enemyTanks.size() > 0) {
            for (int i =0; i < enemyTanks.size(); i++) {
                // 对单个坦克进行判断
                EnemyTank enemyTank = enemyTanks.get(i);

                // 对单个坦克的子弹进行判断
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    // 判断子弹是否击中我方坦克
                    if (shot != null) {
                        if (hero.isLive && shot.isLive) {
                            hitTank(shot, hero);
                        }
                    }
                }
            }
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { // 键盘控制方向  0--上--W  1--右--D 2--下--S 3--左--A
        if(e.getKeyCode() == KeyEvent.VK_W) { //上移动 W
            hero.setDirect(0);
            // 边界值
            if(hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) { //右移动 D
            hero.setDirect(1);
            if(hero.getX() + 80 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) { //下移动 S
            hero.setDirect(2);
            if(hero.getY() < 640) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) { //左移动 A
            hero.setDirect(3);
            if(hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 射击
        if(e.getButton() == MouseEvent.BUTTON1) { //左键

            //  判断 玩家的子弹 是否销毁(单颗)
            //if( hero.shot == null || !hero.shot.isLive) {
            //    hero.shotEnemyTank();
            //}

            // 多颗不判断
            hero.shotEnemyTank();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // 判断子弹是否击中 进行重绘
    public void hitEnemyTank() throws InterruptedException {
       for (int j = 0; j < hero.shots.size(); j++) {
           Shot shot = hero.shots.get(j);
           if(shot != null && shot.isLive) { // 子弹有效
               //遍历敌人坦克
               for(int i = 0; i < enemyTanks.size(); i++) {
                   EnemyTank enemyTank = enemyTanks.get(i);
                   hitTank(shot,enemyTank);
               }
           }
       }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 判断玩家坦克子弹是否击中敌方坦克
            try {
                hitEnemyTank();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 判断敌方坦克是否击中玩家坦克
            try {
                hitHero();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.repaint();
        }
    }
}
