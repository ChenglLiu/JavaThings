package Thread.NewThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 线程池之 newSingleThreadExecutor()
 *      创建含有单个线程的线程池
 * @author liucl
 * @date 2020/8/14 13:45
 */

public class ThreadTest03 {
    public static void main(String[] args) {
        //创建含有单个线程的线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        //主线程4个任务
        for (int i=1; i<5; i++) {
            final int taskId = i;
            //任务加入线程池
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<5; i++) {
                        try {
                            Thread.sleep(50);
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //任务结束
                        System.out.println("第" + taskId + "个任务的第" + i + "次执行");
                    }
                }
            });
        }
        service.shutdown();
        System.out.println("主线程结束");
    }
}
