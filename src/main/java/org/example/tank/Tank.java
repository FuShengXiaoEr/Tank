package org.example.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @ClassName Tank
 * @Description 面向对象，把坦克抽出来，定义该对象属性
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 20:28
 * Version 1.0
 **/
public class Tank {
    // 坦克的初始坐标
    private int x;
    private int y;
    // 坦克的移动速度
    private static int SPEED = 5;
    // 坦克的方向
    private Dir dir;
    // 判断方向健的状态，是按下还是弹起
    private boolean bL,bR,bU,bD;
    // 表示坦克是否移动
    private boolean moving;

    public Tank(){
        this.x = 100;
        this.y = 100;
    }

    public Tank(int x,int y,Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description //绘画坦克，定义初始位置，和大小等等
     *@Date 20:33 2023/5/12
     *@Param [g]
     *@return void
     **/
    public void paint(Graphics g) {
        g.fillRect(x,y,50,50);
        // 每次重新绘制的时候移动，按键按下的时候只是改变方向
        move();
    }

    public void keyPressed(KeyEvent e) {
        // 获取到你拿到的是哪个键
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 之前是直接加减speed移动，现在把这部分抽出来，通过判断按键的状态来设置移动
     *@Date 21:23 2023/5/12
     *@Param []
     *@return void
     **/
    private void setMainDir() {
        if (!bL&&!bU&&!bR&&!bD) {
            moving = false;
            return;
        }else if ( bL&&!bU&&!bR&&!bD){
            dir = Dir.L;
        }else if ( !bL&&!bU&&bR&&!bD){
            dir = Dir.R;
        }else if ( !bL&&bU&&!bR&&!bD){
            dir = Dir.U;
        }else if ( !bL&&!bU&&!bR&&bD){
            dir = Dir.D;
        }
        moving = true;
    }

    // 当按键抬起的时候，移动停止
    // 这里有一个问题需要注意，就是每次我都只能获取到一个按键的动作，因此会出现一个bug就是如果我同时按住两个键，放
    // 开其中一个，但是坦克依然不会移动，这就是因为没有在键放开的时候没有刷新其他健的状态
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        setMainDir();
    }
}
