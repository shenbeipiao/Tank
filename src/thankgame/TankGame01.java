package thankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame01 extends JFrame {
    // 定义 MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame01 tankGame01 = new TankGame01();

    }

    public TankGame01() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的选择： 1. 新游戏   2.上一局游戏");
        String key = scanner.next();
        mp = new MyPanel(key);
        scanner.close();

        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);

        this.setSize(1350,800);
        this.addKeyListener(mp);
        this.addMouseListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // 关闭窗口事件
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }

        });
    }
}
