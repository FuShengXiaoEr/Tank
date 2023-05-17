package org.example.tank.strtegy;

import org.example.tank.Bullet;
import org.example.tank.PlayTank;
import org.example.tank.ResourceMgr;
import org.example.tank.TankFrame;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/16 00:42
 * Version 1.0
 * @description
 **/
public class DefaultFireStrtate implements FireStrategy{
    @Override
    public void fire(PlayTank pt, TankFrame tf) {
        int bx = pt.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int by = pt.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        tf.addBullet(new Bullet(bx, by, pt.getDir(), pt.getGroup(),tf));
    }
}
