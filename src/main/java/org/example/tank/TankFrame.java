package org.example.tank;

import org.example.tank.chainofresponsibility.BulletTankCollider;
import org.example.tank.chainofresponsibility.BulletWallCollider;
import org.example.tank.chainofresponsibility.Collider;
import org.example.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName TankFram
 * @Description TODO
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 19:50
 * Version 1.0
 **/
public class TankFrame extends Frame {
//    public static final TankFrame INSTANCE = new TankFrame();
//    private int x = 100 ,y = 100; 因此这里就不要直接定义坦克的位置了，因为抽离除了坦克这个对象
//    private static int SPEED = 5; 这也是坦克的属性
    private PlayTank myTank;
    public static int GAME_WIDTH = 800;
    public static int GAME_HEIGHT = 600;
    List<AbstractGameObject> objects = new ArrayList<>();
    ColliderChain chain = new ColliderChain();

    public TankFrame(){
        super("tank war");
        this.setLocation(-900,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        // 增加键盘的监听
        this.addKeyListener(new TankKeyListener());
        initGameObjects();
    }



    /**
     *@Author XiZhuangBaoTu
     *@Description 初始坦克对象
     *@Date 01:07 2023/5/15
     *@Param []
     *@return void
     **/
    private void initGameObjects() {
        // 初始化一辆坦克,这里不定义坦克的属性，是因为绘制坦克是坦克自己的行为
        myTank = new PlayTank(100,100,Dir.R,Group.Good,this);
        objects = new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < tankCount; i++) {
            objects.add(new Tank(100+50*i,200,Dir.D,Group.Bad,this));
        }
        this.add(new Wall(100,100,400,20));
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
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bllutes:"+bullets.size(),10,60);
//        g.drawString("enemies:"+enemy.size(),10,80);
        g.setColor(c);
//        // 绘制方块
//       g.fillRect(x,y,50,50);
       // 坦克自己决定自己的模样
        myTank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }
            AbstractGameObject go = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                chain.collide(objects.get(i),objects.get(j));
            }
            if (objects.get(i).isLive()) {
                objects.get(i).paint(g);
            }
        }

/*        for (int i = 0; i < enemy.size(); i++) {
            if (!enemy.get(i).isLive()) {
                enemy.remove(i);
            }else {
                enemy.get(i).paint(g);
            }
        }
        for (int i = 0;i < bullets.size(); i++) {
            // 和敌机碰撞
            for (int j = 0; j < enemy.size(); j++) {
                bullets.get(i).collidesWithTank(enemy.get(j));
            }
            if (!bullets.get(i).isLive()) {
                bullets.remove(i);
            }else {
                bullets.get(i).paint(g);
            }
        }
        for (int i = 0; i < explodes.size(); i++) {
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            }else {
                explodes.get(i).paint(g);
            }
        }*/
    }

    public void add(Explode explode) {
        this.objects.add(explode);
    }

    public void add(AbstractGameObject object) {
        objects.add(object);
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
        this.objects.add(bullet);
    }

}
