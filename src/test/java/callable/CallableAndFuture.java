package callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/19 20:39
 * Version 1.0
 * @description
 **/
public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService servicev= Executors.newCachedThreadPool();
        Future<String> submit = servicev.submit(new MyTask());

        String s = submit.get();
        System.out.printf(s);
        System.out.println();
        System.out.printf("main thread finish");
    }
}
