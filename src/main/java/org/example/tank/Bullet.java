package org.example.tank;

import java.awt.*;

/**
 * @ClassName Bullet
 * @Description TODO
 * @Author XiZhuangBaoTu
 * Date 2023/5/13 02:48
 * Version 1.0
 **/
public class Bullet {
    // 子弹的初始位置
    private int x,y;
    // 子弹的速度
    private int speed;
    // 子弹的方向
    private Dir dir;
    // 区分是好子弹还是坏子弹
    private Group group;

    // 坦克的移动速度
    private static int SPEED = 5;

    public Bullet(int x,int y,Dir dir,Group group) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.group = group;
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 画子弹
     *@Date 02:50 2023/5/13
     *@Param [g]
     *@return void
     **/
    public void paint(Graphics g) {
        if (this.group == Group.Good) {
            switch (dir) {
                case L:
                    g.drawImage(ResourceMgr.bulletL, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.bulletR, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.bulletU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.bulletD, x, y, null);
                    break;
            }
        }
        // 每次重新绘制的时候移动，按键按下的时候只是改变方向
        move();
    }

    private void move() {
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
}
