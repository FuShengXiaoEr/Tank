package org.example.tank;

import java.awt.*;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/17 15:19
 * Version 1.0
 * @description
 **/
public class Wall extends AbstractGameObject{
    private int x,y,w,h;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 画一个墙
     *@Date 15:20 2023/5/17
     *@Param [g]
     *@return void
     **/
    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.gray);
        g.fillRect(x,y,w,h);
        g.setColor(color);
    }
}
