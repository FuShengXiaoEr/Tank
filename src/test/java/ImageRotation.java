import org.example.tank.ResourceMgr;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRotation {
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

    public static void main(String[] args) {
        try {
            // 读取原始图像
            BufferedImage originalImage = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));

            // 旋转角度
            int rotationDegree = -90;

            // 执行图像旋转
            BufferedImage rotatedImage = rotateImage1(originalImage, rotationDegree);

            // 输出旋转后的图像到文件
            File outputImageFile = new File("src/main/resources/images/image_new_-90.png");
            ImageIO.write(ResourceMgr.goodTankL, "png", outputImageFile);

            System.out.println("旋转后的图像已保存到文件: " + outputImageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
