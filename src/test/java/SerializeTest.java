import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/19 16:08
 * Version 1.0
 * @description
 **/
public class SerializeTest {
    @Test
    public void testLoadImage(){
        try {
            T t = new T();
            File f = new File("src/test/java/s.dat");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.flush();
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadLoad(){
        try {
            File f = new File("src/test/java/s.dat");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            T o = (T) ois.readObject();
            Assertions.assertEquals(4,o.m);
            Assertions.assertEquals(8,o.n);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// 这里不能写成内部类，内部类序列化需要外部类也序列化，
class T implements Serializable {
    public int m = 4;
    // transient 不参与序列化
    public int n = 8;
}
