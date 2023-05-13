package org.example.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @ClassName TankFram
 * @Description TODO
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 19:50
 * Version 1.0
 **/
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();
//    private int x = 100 ,y = 100; 因此这里就不要直接定义坦克的位置了，因为抽离除了坦克这个对象
//    private static int SPEED = 5; 这也是坦克的属性
    private Tank myTank;
    private Tank enemy;
    private static int GAME_WIDTH = 800;
    private static int GAME_HEIGHT = 600;
    private Bullet bullet;

    private TankFrame(){
        this.setLocation(-900,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setVisible(true);
        // 增加键盘的监听
        this.addKeyListener(new TankKeyListener());
        // 初始化一辆坦克,这里不定义坦克的属性，是因为绘制坦克是坦克自己的行为
        myTank = new Tank(100,100,Dir.R,Group.Good);
        // NPC敌人坦克
        enemy = new Tank(200,200,Dir.D,Group.Bad);
        bullet = new Bullet(100,100,Dir.D,Group.Bad);
    }

    Image offScreenImage = null;
    /**
     *@Author XiZhuangBaoTu
     *@Description 解决双缓冲的问题
     *@Date 02:24 2023/5/13
     *@Param [g]
     *@return void
     **/
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        // 最后才一次性刷到内存中
        g.drawImage(offScreenImage,0,0,null);
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 这个方法自动会给你调用，当重新绘画页面的时候
     *@Date 19:55 2023/5/12
     *@Param [g]
     *@return void
     **/
    @Override
    public void paint(Graphics g) {
//        // 绘制方块
//       g.fillRect(x,y,50,50);
       // 坦克自己决定自己的模样
        myTank.paint(g);
        enemy.paint(g);
        bullet.paint(g);
    }


    /**
     *@Author XiZhuangBaoTu
     *@Description 坦克的监听类,keyadapter这个不是适配器模式
     *@Date 20:13 2023/5/12
     *@Param
     *@return
     **/
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    public void addBullet(Bullet bullet){
        this.bullet = bullet;
    }

}
