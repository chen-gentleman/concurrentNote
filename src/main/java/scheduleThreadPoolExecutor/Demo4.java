package scheduleThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**定时任务有异常会怎么样？
 * @Author @Chenxc
 * @Date 2022/5/20 11:18
 */
public class Demo4 {
    public static void main(String[] args) throws InterruptedException{
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        System.out.println(System.currentTimeMillis());
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);


        /**
         * public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
         * long initialDelay,
         * long delay,
         * TimeUnit unit);
         * 4个参数：
         * command：表示要执行的任务
         * initialDelay：表示延迟多久执行第一次
         * period：表示下次执行时间和上次执行结束时间之间的间隔时间
         * unit：参数2和参数3的时间单位，是个枚举，可以是天、小时、分钟、秒、毫秒、纳秒等
         */
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            int currCount = count.getAndIncrement();
            System.out.println(Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + "第" + currCount +
                    "次" + "开始执行");
            System.out.println(10 / 0);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "第" + currCount +
                    "次" + "执行结束");

        }, 1, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);
        System.out.println(scheduledFuture.isCancelled());
        System.out.println(scheduledFuture.isDone());
    }
}
