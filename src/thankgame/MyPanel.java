package thankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * version 1.0
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel implements KeyListener , MouseListener, Runnable {
    // 定义玩家坦克
    Hero hero = null;
    // 电脑坦克 放入 Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTanksSize = 3;

    public MyPanel() {
        hero = new Hero(100,100);

        //初始化电脑坦克
        for(int i = 0; i < enemyTanksSize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (1 + i), 0);
            enemyTank.setDirect(2);
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // 调用父类方法完成初始化
        g.fillRect(0,0,1000,750);// 填充矩形，默认黑色

        // 画出玩家坦克 将其封装为单独的方法
        drawTank(hero.getX(), hero.getY(), g,hero.getDirect(),1);
        // 画出电脑坦克 遍历Vector
        for(int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);
        }

        // 子弹
        if(hero.shot != null && hero.shot.isLive == true) {
            g.draw3DRect(hero.shot.x,hero.shot.y,4,4,false);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { // 键盘控制方向  0--上--W  1--右--D 2--下--S 3--左--A
        if(e.getKeyCode() == KeyEvent.VK_W) { //上移动 W
            hero.setDirect(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) { //右移动 D
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) { //下移动 S
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) { //左移动 A
            hero.setDirect(3);
            hero.moveLeft();
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 射击
        if(e.getButton() == MouseEvent.BUTTON1) { //左键
            hero.shotEnemyTank();
        }

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

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.repaint();
        }
    }
}
