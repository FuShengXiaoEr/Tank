package org.example.tank.chainofresponsibility;

import org.example.tank.AbstractGameObject;
import org.example.tank.Tank;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 11:21
 * Version 1.0
 * @description
 **/
public class TankTankCollider implements Collider {
    @Override
    public boolean collider(AbstractGameObject o1, AbstractGameObject o2) {
        if (o1 != o2 && o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;
            if (t1.isLive() && t2.isLive()) {
                if (t1.getRectangle().intersects(t2.getRectangle())) {
                    t1.back();
                    t2.back();
                }
            }
        }
        return true;
    }
}
