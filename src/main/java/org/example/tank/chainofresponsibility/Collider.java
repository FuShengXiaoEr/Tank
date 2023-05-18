package org.example.tank.chainofresponsibility;

import org.example.tank.AbstractGameObject;

/**
 * 责任链模式
 * @author XiZhuangBaoTu
 * Date 2023/5/17 16:14
 * Version 1.0
 * @description
 **/
public interface Collider {
    // return true:chain go on return false :chain break;
    boolean collider(AbstractGameObject o1,AbstractGameObject o2);
}
