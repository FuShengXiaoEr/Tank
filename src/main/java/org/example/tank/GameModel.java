package org.example.tank;

import org.example.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用MVC 分离出视图模型
 * @author XiZhuangBaoTu
 * Date 2023/5/18 11:31
 * Version 1.0
 * @description
 **/
public class GameModel implements Serializable {
    // 主战坦克
    private PlayTank myTank;
    // 碰撞检测链
    ColliderChain chain = new ColliderChain();
    // 游戏对象实体
    List<AbstractGameObject> objects = new ArrayList<>();

    public GameModel(TankFrame tk){
        initGameObjects(tk);
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 初始坦克对象
     *@Date 01:07 2023/5/15
     *@Param []
     *@return void
     **/
    private void initGameObjects(TankFrame tk) {
        // 初始化一辆坦克,这里不定义坦克的属性，是因为绘制坦克是坦克自己的行为
        myTank = new PlayTank(100,100,Dir.R,Group.Good,tk);
        objects = new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < tankCount; i++) {
            objects.add(new Tank(100+100*i,200,Dir.D,Group.Bad,tk));
        }
        this.add(new Wall(100,100,400,20));
    }

    public void add(AbstractGameObject object) {
        objects.add(object);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.setColor(c);
        myTank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }
            AbstractGameObject go = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                chain.collide(objects.get(i),objects.get(j));
            }
            if (objects.get(i).isLive()) {
                objects.get(i).paint(g);
            }
        }
    }

    public PlayTank getMyTank(){
        return myTank;
    }
}
