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
//    private int x = 100 ,y = 100; 因此这里就不要直接定义坦克的位置了，因为抽离除了坦克这个对象
//    private static int SPEED = 5; 这也是坦克的属性
    private Tank myTank;
    private Tank enemy;


    public TankFrame(){
        this.setLocation(-900,100);
        this.setSize(800,600);
        this.setVisible(true);
        // 增加键盘的监听
        this.addKeyListener(new TankKeyListener());
        // 初始化一辆坦克,这里不定义坦克的属性，是因为绘制坦克是坦克自己的行为
        myTank = new Tank(100,100,Dir.R);
        // NPC敌人坦克
        enemy = new Tank(200,200,Dir.D);
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
//        enemy.paint(g);
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
}
