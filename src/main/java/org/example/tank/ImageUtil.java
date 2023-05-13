package org.example.tank;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


/**
 * @description 图像工具
 * @author XiZhuangBaoTu
 * Date 2023/5/13 01:02
 * Version 1.0
 **/
public class ImageUtil {
    public static BufferedImage rotateImage1(final BufferedImage bufferedImage,final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage image;
        Graphics2D graphics2D;
        (graphics2D = (image = new BufferedImage(w,h,type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree),w/2,h/2);
        graphics2D.drawImage(bufferedImage,0,0,null);
        graphics2D.dispose();
        return image;
    }

    public static BufferedImage rotateImage(BufferedImage bufferedImage, int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();

        BufferedImage rotatedImage = new BufferedImage(w, h, type);
        Graphics2D g2d = rotatedImage.createGraphics();

        // 设置旋转角度和旋转中心
        double rotationAngle = Math.toRadians(degree);
        double cx = w / 2.0;
        double cy = h / 2.0;

        // 执行图像旋转
        AffineTransform transform = new AffineTransform();
        transform.rotate(rotationAngle, cx, cy);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(bufferedImage, transform, null);
        g2d.dispose();

        return rotatedImage;
    }

}
