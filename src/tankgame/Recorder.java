package tankgame;

import java.io.*;
import java.util.Vector;

/**
 * 用于记录相关信息和文件交互
 */
public class Recorder {
    // 定义变量 记录击败坦克数
    private static int allEnemyTankNum = 0;
    // 定义 IO 对象，将数据写到文件中去
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;

    // 文件位置 自定义
    private static String recordFile = "d:\\myRecord.txt";

    //定义一个 Node 的 Vector  用于保存敌人坦克信息
    private static Vector<Node> nodes = new Vector<>();

    // 定义 Vector 指向 MyPanel 对象的敌人坦克 Vector
    private static Vector<EnemyTank> enemyTanks = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    // 当我方击毁一个敌方坦克时击败坦克数加一
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    // 读取 recordFile 文件信息 恢复数据
    public static Vector<Node> getNodeAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            // 循环读取数据 恢复有效坦克数据
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                 Node node = new Node(Integer.parseInt(xyd[0]),Integer.parseInt(xyd[0]),Integer.parseInt(xyd[0]));
                 nodes.add(node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nodes;
    }

    // 当游戏退出时 保存信息
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            // 遍历敌人坦克的 Vector ，保存坐标和方向
            for (int i = 0; i < enemyTanks.size(); i++) {
                // 取出坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                // 判断有效性
                if(enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    // 写入到文件
                    bw.write(record + "\r\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String getRecordFile() {
        return recordFile;
    }
}
