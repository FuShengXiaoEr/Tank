package org.example.nettycodec;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/20 11:00
 * Version 1.0
 * @description
 **/
public class TaskMsg {
    public int x;
    public int y;

    public TaskMsg(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TaskMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
