package org.example.tank.chainofresponsibility;

import org.example.tank.AbstractGameObject;
import org.example.tank.Tank;
import org.example.tank.Wall;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 10:35
 * Version 1.0
 * @description
 **/
public class TankWallCollider implements Collider{
    @Override
    public boolean collider(AbstractGameObject o1, AbstractGameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;
            if (t.getRectangle().intersects(w.getRect())) {
                t.back();
            }
        }
        return true;
    }
}
