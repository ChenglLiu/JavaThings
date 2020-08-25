package Thread.NewThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desription 线程池的常用方法之 newCachedThreadPool()
 *      可根据需要自动创建新线程的线程池，以前创建线程可用时，将重用他们
 * @author liucl
 * @date 2020/8/14 13:20
 */

public class ThreadTest02 {
    public static void main(String[] args) {
        //创建一个可根据需要创建新线程的线程池，以前构造的线程在可用的时候将重用他们
        //执行短期异步任务的程序而言，这些线程池可以提高程序性能
        ExecutorService service = Executors.newCachedThreadPool();
        //主线程创建4个任务
        for (int i=1; i<5; i++) {
            final int taskId = i;
            //任务加入线程池
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<5; i++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //任务结束
                        System.out.println("第" + taskId + "个任务的" + i + "的第次执行");
                    }
                }
            });
        }
        service.shutdown();
        System.out.println("主线程结束");
    }
}
