package scheduleThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**使用 ScheduleThreadPoolExecutor的scheduleWithFixedDelay 方法，该方法设置了执行周期，与
 scheduleAtFixedRate方法不同的是，下一次执行时间是上一次任务执行完的系统时间加上period，因
 而具体执行时间不是固定的，但周期是固定的，是采用相对固定的延迟来执行任务
 * @Author @Chenxc
 * @Date 2022/5/20 11:18
 */
public class Demo5 {
    public static void main(String[] args)throws InterruptedException {
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
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "第" + currCount +
                    "次" + "执行结束");

        }, 1, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);

        /**
         * cancel 方法，参数表示是否给任务
         * 发送中断信号。即是否允许中断正在执行的任务
         */
        scheduledFuture.cancel(false);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("任务是否被取消："+scheduledFuture.isCancelled());
        System.out.println("任务是否已完成："+scheduledFuture.isDone());
    }
}
