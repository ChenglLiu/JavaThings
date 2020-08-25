package Thread.NewThread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desrciption 线程池的几个常用方法之 newFixedThreadPool(int n)
 *      创建一个可重用的固定线程数的线程池
 * @author liucl
 * @date 2020/8/14 10:10
 */

public class ThreadTest01 {
    public static void main(String[] args) {
        //创建线程池，数量为3
        ExecutorService service = Executors.newFixedThreadPool(3);
        //主线程创建4个任务
        for (int i=1; i<5; i++) {
            final int taskId = i;
            //任务加入线程池
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<5; i++) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //任务执行后
                        System.out.println("第"+ taskId + "个任务的第" + i +"次执行");
                    }
                }
            });
        }
        service.shutdown();
        System.out.println("主线程结束");
    }
}
