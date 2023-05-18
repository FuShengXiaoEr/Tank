package org.example.tank.chainofresponsibility;

import org.example.tank.*;

import java.awt.*;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/17 16:15
 * Version 1.0
 * @description
 **/
public class BulletTankCollider implements Collider{
    @Override
    public boolean collider(AbstractGameObject o1, AbstractGameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            // 坦克已经死了，就不需要再进行碰撞检测，坦克只是隐藏在原位置
            if (!bullet.isLive() || !tank.isLive()) {
                return false;
            }
            if (bullet.getGroup() == tank.getGroup()) {
                return true;
            }
            Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());

            if ((bullet.getRect().intersects(rectTank))) {
                bullet.die();
                tank.die();
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collider(o2,o1);
        }
        return true;
    }
}
