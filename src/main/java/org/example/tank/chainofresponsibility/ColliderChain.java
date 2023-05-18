package org.example.tank.chainofresponsibility;

import org.example.tank.AbstractGameObject;
import org.example.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * 碰撞检测链条，当第一个碰撞，子弹就死了，后面的碰撞检测都取消
 *
 * @author XiZhuangBaoTu
 * Date 2023/5/18 10:48
 * Version 1.0
 * @description
 **/
public class ColliderChain {
    private List<Collider> colliders = new ArrayList<>();

    public ColliderChain() {
        initColliders();
    }

    private void initColliders() {
        String[] collidersString = PropertyMgr.get("colliders").split(",");
        try {
            for (String name : collidersString) {
                Class<?> aClass = Class.forName("org.example.tank.chainofresponsibility." + name);
                colliders.add((Collider) (aClass.getConstructor().newInstance()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 让collite链条上，让colliter自己根据返回值判断是否继续往下校验
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for (Collider collider : colliders) {
            if (!collider.collider(go1, go2)) {
                return false;
            }
        }
        return true;
    }
}
