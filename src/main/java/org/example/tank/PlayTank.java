package org.example.tank;

import org.example.tank.strtegy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @ClassName Tank
 * @Description 面向对象，把坦克抽出来，定义该对象属性（主战坦克）
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 20:28
 * Version 1.0
 **/
public class PlayTank {
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
    private boolean moving;
    // 区分是敌人还是主战坦克
    private Group group;
    private boolean live = true;
    // 开枪策略
    private FireStrategy fireStrategy;

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

    public PlayTank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        this.initFireStrategy();
    }

    /**
     * @return void
     * @Author XiZhuangBaoTu
     * @Description //绘画坦克，定义初始位置，和大小等等
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
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
        }
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
     * @return void
     * @Author XiZhuangBaoTu
     * @Description 之前是直接加减speed移动，现在把这部分抽出来，通过判断按键的状态来设置移动
     * @Date 21:23 2023/5/12
     * @Param []
     **/
    private void setMainDir() {
        if (!bL && !bU && !bR && !bD) {
            moving = false;
            return;
        } else if (bL && !bU && !bR && !bD) {
            dir = Dir.L;
        } else if (!bL && !bU && bR && !bD) {
            dir = Dir.R;
        } else if (!bL && bU && !bR && !bD) {
            dir = Dir.U;
        } else if (!bL && !bU && !bR && bD) {
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
            case KeyEvent.VK_CONTROL:
                // 发射子弹
                fire();
                break;
        }
        setMainDir();
    }

    FireStrategy strategy = null;
    public void initFireStrategy(){
        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class clazz = Class.forName("org.example.tank.strtegy." + className);
            strategy = (FireStrategy) clazz.getDeclaredConstructor().newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fire() {

        strategy.fire(this,tankFrame);
    }

    public void die() {
        this.setLive(false);
    }
}
