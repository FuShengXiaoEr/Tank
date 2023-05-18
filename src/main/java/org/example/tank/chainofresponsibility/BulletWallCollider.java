package org.example.tank.chainofresponsibility;

import org.example.tank.AbstractGameObject;
import org.example.tank.Bullet;
import org.example.tank.Tank;
import org.example.tank.Wall;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/17 16:43
 * Version 1.0
 * @description
 **/
public class BulletWallCollider implements Collider {
    @Override
    public boolean collider(AbstractGameObject o1, AbstractGameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet bullet = (Bullet) o1;
            Wall wall = (Wall) o2;
            if (bullet.isLive()) {
                if (bullet.getRect().intersects(wall.getRect())) {
                    bullet.die();
                    return false;
                }
            } else if (o1 instanceof Tank && o2 instanceof Bullet) {
                return collider(o2, o1);
            }
        }
        return true;
    }
}
