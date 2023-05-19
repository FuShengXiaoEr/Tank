package org.example.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/17 15:26
 * Version 1.0
 * @description
 **/
public abstract class AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
