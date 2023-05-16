package org.example.tank;

import java.awt.*;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/15 01:20
 * Version 1.0
 * @description
 **/
public class Explode {
    private int x,y;
    private int width,height;
    private int stp = 0;
    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }

    public void paint(Graphics g) {
        if (!live) {
            return;
        }
         g.drawImage(ResourceMgr.explodes[stp],x,y,null);
         stp++;
         if (stp >= ResourceMgr.explodes.length) {
             this.live = false;
         }
    }
}
