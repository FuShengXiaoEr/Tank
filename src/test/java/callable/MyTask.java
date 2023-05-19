package callable;

import java.util.concurrent.Callable;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/19 20:38
 * Version 1.0
 * @description
 **/
public class MyTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + "  + " + "add!");
        }
        return "finish";
    }
}
