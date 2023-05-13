

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @ClassName ImageTest
 * @Description TODO
 * @Author XiZhuangBaoTu
 * Date 2023/5/13 00:15
 * Version 1.0
 **/
public class ImageTest {
    @Test
    public void testLoadIMage(){
        try {
            BufferedImage image = ImageIO.read(new File("/Users/chen/Documents/GitHub/Tank/src/main/resources/images/bulletR.gif"));
            assertNotNull(image);

            BufferedImage image1 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            assertNotNull(image1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRotateImage(){
        try {
            BufferedImage bufferedImage = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("`images/GoodTank1.png`"));
            BufferedImage image = rotateImage(bufferedImage,90);
            assertNotNull(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BufferedImage rotateImage(final BufferedImage bufferedImage,final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage image;
        Graphics2D graphics2D;
        (graphics2D = (image = new BufferedImage(w,h,type))
                .createGraphics()).setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree));
        graphics2D.drawImage(bufferedImage,0,0,null);
        graphics2D.dispose();
        return image;
    }
}
