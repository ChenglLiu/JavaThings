package Thread.NewThread;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description 创建一个安排在给定延迟时间后运行 或  可定期执行的线程池
 * @author liucl
 * @date 2020/8/14 14:00
 */

public class ThreadTest04 {
    public static void main(String[] args) {
        //创建线程池
        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
        //5秒后执行任务
        scheduledService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.print(Calendar.getInstance().getTime());
                System.out.println(" boom!");
            }
        }, 5, TimeUnit.SECONDS);
        //5秒后执行任务，以后每2秒执行一次
        scheduledService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.print(Calendar.getInstance().getTime());
                System.out.println("...BOOM!!!");
            }
        }, 5, 2, TimeUnit.SECONDS);
    }
}
