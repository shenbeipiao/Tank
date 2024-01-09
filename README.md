# 坦克大战
这是我在学习 Java 基础时第一个较为正式的项目（**跟做**），只使用基础的 Java 知识，视频教程是 ：【【零基础 快速学Java】韩顺平 零基础30天学会Java】 https://www.bilibili.com/video/BV1fh411y7R8/?share_source=copy_web&vd_source=57b5284b7f71eaf337f0c159cae09c41

这个项目是从头开始将代码放在 Github 仓库的，我通过 Git 来进行代码提交更新，在学习巩固 Java 基础的同时实践 Git 的相关操作。

虽然是跟做，但也是收获颇多，总结了一份不正式的开发文档（开发过程的总结）。
如有需要，可做参考。
## 开发涉及到的知识点（基础）
- Java 面向对象编程
- 多线程
- 文件 I/O 操作
- 数据库

## 游戏界面
### Java 绘图坐标体系
![img.png](pages/img.png)
### Java 绘图技术
Component 类提供了两个和绘图相关的重要方法：

- paint(Graphics g)    ：  绘制组件外观
- repaint()     ： 刷新组件外观

> 当组件第一次在屏幕显示的时候，程序会自动调用 paint()  方法来绘制组件

在这三种情况下 paint()  方法将会被调用：

1. 窗口最小化，再最大化
2. 窗口的大小发生变化
3. repaint()  方法被调用

Graphics 类（图形类）是所有图形上下文的抽象基类，允许应用程序在各种设备上实现的组件以及屏幕外图像上绘图。
我们以此类来进行坦克及其他元素的绘制。

**主要方法：**

1.画直线：drawLine(int x1,int y1,int x2,int y2)  

2.画矩形边框：drawRect(int x,int y,int width,int height)

3.画椭圆边框：drawOval((int x,int y,int width,int height)

4.填充矩形：fillRect(int x,int y,int width,int height)

5.填充椭圆：fillOval(int x,int y,int width,int height)

6.画图片：drawlmage(Image img,int x,int y,)

7.画字符串：drawString(String str,int x,inty)

8.设置画笔的字体：setFont(Font font)

9.设置画笔的颜色：setColor(Color c)

官方文档

https://docs.oracle.com/javase/8/docs/api/

### Java 事件处理机制
java 事件处理是采取 “**委派事件模型**” 。当 事件发生时，产生事件的对象会把此 “ 信息 ”  传递给 “事件的监听者”  处理。 “信息”  即 java.awt.event  是事件类库里某个类所创建的对象，把它称之为 “事件的对象” 。

事件源：事件源是一个产生事件的对象，比如按钮、窗口等。

事件：事件就是承载事件源状态改变时的对象，比如当键盘事件、鼠标事件、窗口事件等会产生一个事件对象，该对象保存着当前事件很多信息，比如 KeyEevent 对象有被按下键的 code 值。

目前游戏中有两个事件：
- `W A S D` 方向键控制玩家坦克的移动
- `鼠标左键` 控制子弹的发射（视频教程中是 `J` 键）


**java.awt.event 包 和 javax.swing.event 包中定义了各种事件类型。**
![img_2.png](pages/img_2.png)

## 游戏分解 N 个类
`Tank` 类 定义了坦克的相关属性（角色、方向、坦克的坐标）以及移动坦克的四个方法 `moveUp`、`moveDown`、`moveDown`、`moveLeft`、`moveRight`。

`Hero` 类继承了 Tank 类，相当于是玩家坦克。

`MyPanel` 类继承了 JPanel 类以及 KeyListener 接口类，在此类中定义了画绘制坦克的方法 `DrawTank`, 监听按键并调用相应移动方法的`keyPressed`。

`Shot` 类 定义了子弹的属性（横纵坐标、方向、速度）及方法，继承了 `Thread`，相当于是一个线程。

`TankGame` 类则是程序的主类，完成程序初始化。
在Swing中，任何其他组件都必须位于一个顶层容器中。JFrame 窗口和 JPanel 面板是常用的顶层容器。

**坦克的绘制**

坦克是由五部分组成，我们通过以下方法进行绘制：
- fillRect(int x,int y,int width,int height), 其中 `x` 和 `y` 是矩形相对于坐标系原点的横纵坐标，`width` 和 `height` 是矩形的宽高。
- fillOval(int x,int y,int width,int height), 其中 `x` 和 `y` 是椭圆相对于坐标系原点的横纵坐标，`width` 和 `height` 是宽高，相等时即为圆。
- drawLine(int x1,int y1,int x2,int y2), 其中`(x1,y1)` 和 `(x2,y2)` 是坦克炮筒（直线）的起点和终点坐标。
  
`super.paint(g);` 调用父类方法完成初始化,重写方法时不能删去。
~~~java 
@Override
    public void paint(Graphics g) {
        super.paint(g); // 调用父类方法完成初始化
        g.fillRect(0,0,1000,750);// 填充矩形，默认黑色

        // 画出坦克 将其封装为单独的方法
        drawTank(hero.getX(), hero.getY(), g,hero.getDirect(),0);

    }