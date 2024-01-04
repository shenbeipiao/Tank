package thankgame;

import javax.swing.*;
import java.awt.*;

/**
 * version 1.0
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel {
    // 定义玩家坦克
    Hero hero = null;

    public MyPanel() {
        hero = new Hero(100,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // 调用父类方法完成初始化
        g.fillRect(0,0,1000,750);// 填充矩形，默认黑色
    }
}
