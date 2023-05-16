package org.example.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/16 00:09
 * Version 1.0
 * @description
 **/
public class PropertyMgr {
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return (String) props.get(key);
    }
}
