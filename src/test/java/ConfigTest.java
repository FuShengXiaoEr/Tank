import org.example.tank.PropertyMgr;
import org.junit.jupiter.api.Test;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/16 00:22
 * Version 1.0
 * @description
 **/
public class ConfigTest {
    @Test
    public void ConfigTest(){
        System.out.println(PropertyMgr.get("initTankCount"));
    }
}
