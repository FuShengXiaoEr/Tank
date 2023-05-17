package org.example.tank.strtegy;

import org.example.tank.*;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/16 06:55
 * Version 1.0
 * @description
 **/
public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(PlayTank pt, TankFrame tf) {
        int bx = pt.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int by = pt.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        for (Dir d : Dir.values()) {
            tf.addBullet(new Bullet(bx, by, d, pt.getGroup(), tf));
        }
    }
}
