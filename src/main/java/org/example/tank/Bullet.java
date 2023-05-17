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
    private int x, y;
    // 子弹的速度
    private int speed;
    // 子弹的方向
    private Dir dir;
    // 区分是好子弹还是坏子弹
    private Group group;
    private TankFrame tf;

    // 子弹的移动速度
    private static int SPEED = 5;

    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Bullet(int x, int y, Dir dir, Group group,TankFrame tf) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.group = group;
        this.tf = tf;
    }

    /**
     * @return void
     * @Author XiZhuangBaoTu
     * @Description 画子弹
     * @Date 02:50 2023/5/13
     * @Param [g]
     **/
    public void paint(Graphics g) {
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
        // 每次重新绘制的时候移动，按键按下的时候只是改变方向
        move();
    }

    // 移动前先进行碰撞检测
    public void collidesWithTank(Tank tank) {
        // 坦克已经死了，就不需要再进行碰撞检测，坦克只是隐藏在原位置
        if (!this.live || !tank.isLive()) {
            return;
        }
        if (this.group == tank.getGroup()) {
            return;
        }
        Rectangle rect = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());

        if (rect.intersects(rectTank)) {
            die();
            tank.die();
            tf.add(new Explode(x,y));
        }

    }

    private void die() {
        this.live = false;
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
        boundCheck();
    }

    /**
     * @return void
     * @Author XiZhuangBaoTu
     * @Description 检查子弹出界
     * @Date 07:58 2023/5/13
     * @Param []
     **/
    private void boundCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
