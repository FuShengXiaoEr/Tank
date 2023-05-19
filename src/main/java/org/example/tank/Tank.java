package org.example.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Random;

/**
 * @ClassName Tank
 * @Description 面向对象，把坦克抽出来，定义该对象属性
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 20:28
 * Version 1.0
 **/
public class Tank extends AbstractGameObject {
    // 坦克的初始坐标
    private int x;
    private int y;
    // 坦克的移动速度
    private static int SPEED = 5;
    // 坦克的方向
    private Dir dir;
    // 判断方向健的状态，是按下还是弹起
    private boolean bL, bR, bU, bD;
    // 表示坦克是否移动
    private boolean moving = true;
    // 区分是敌人还是主战坦克
    private Group group;
    private boolean live = true;
    // 设置上一次的位置，当到达边界后就返回
    private int oldx, oldy;
    // 给坦克一个单独的高度
    private int width ,height;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private TankFrame tankFrame;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() {
        return group;
    }

    public Tank() {
        this.x = 100;
        this.y = 100;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        this.oldx = x;
        this.oldy = y;
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();

        this.rectangle = new Rectangle(x,y,this.width,this.height);
    }

    /**
     * @return void
     * @Author XiZhuangBaoTu
     * @Description //绘画坦克，定义初始位置，和大小等等（敌人坦克—）
     * @Date 20:33 2023/5/12
     * @Param [g]
     **/
    public void paint(Graphics g) {
        // 画矩形
//        g.fillRect(x,y,50,50);
//        try {
//            BufferedImage bufferedImage = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
//            g.drawImage(bufferedImage,x,y,null);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        if (!this.isLive()) return;
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
        }
        // 每次重新绘制的时候移动，按键按下的时候只是改变方向
        move();
    }

    private void move() {
        if (!moving) return;
        oldx = x;
        oldy = y;
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
        randomDir();
        if (random.nextInt(100) > 90) {
            fire();
        }

        // 每次移动的时候都记录下rectangle的位置，为了避免每次move的时候都去new Rectangle
        rectangle.x = x;
        rectangle.y = y;
    }

    private void boundCheck() {
        if (x < 0 || y < 30 || x+ width > TankFrame.GAME_WIDTH || y + height> TankFrame.GAME_HEIGHT) {
            this.back();
        }
    }

    public void back() {
        this.x = oldx;
        this.y = oldy;
    }

    /**
     * @Author XiZhuangBaoTu
     * @Description 敌人坦克随机改变方向
     * @Date 00:31 2023/5/14
     * @Param []
     * @return void
     **/
    private Random random = new Random();

    private void randomDir() {
        if (random.nextInt(100) > 95) {
            this.dir = Dir.values()[random.nextInt(Dir.values().length)];
        }
    }

    private void fire() {
        int bx = x + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int by = y + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        tankFrame.addBullet(new Bullet(bx, by, dir, group,tankFrame));
    }

    public void die() {
        this.setLive(false);
        tankFrame.add(new Explode(x,y));
    }
}
