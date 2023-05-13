package org.example.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName ResourceMgr
 * @Description 把图片都加载到缓存中，不用每次都去硬盘上加载
 * @Author XiZhuangBaoTu
 * Date 2023/5/13 00:53
 * Version 1.0
 **/
public class ResourceMgr {
    // 好的坦克
    public static BufferedImage goodTankL,goodTankR,goodTankU,goodTankD;
    // 坏的坦克
    public static BufferedImage badTankL,badTankR,badTankU,badTankD;
    // 子弹的图片
    public static BufferedImage bulletL,bulletR,bulletU,bulletD;
    // 爆炸效果图
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankL = ImageUtil.rotateImage(goodTankU,-90);
            goodTankD = ImageUtil.rotateImage(goodTankU,180);
            goodTankR = ImageUtil.rotateImage(goodTankU,90);

            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankL = ImageUtil.rotateImage(badTankU,-90);
            badTankD = ImageUtil.rotateImage(badTankU,180);
            badTankR = ImageUtil.rotateImage(badTankU,90);

            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU,-90);
            bulletD = ImageUtil.rotateImage(bulletU,180);
            bulletR = ImageUtil.rotateImage(bulletU,90);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }
        }catch (IOException e ) {
            e.printStackTrace();
        }
    }


}
