package scheduleThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**schedule:延迟执行任务1次
 * @Author @Chenxc
 * @Date 2022/5/20 10:39
 */
public class Demo1 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        System.out.println(System.currentTimeMillis());
        /**
         * public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
         * 3个参数：
         * command：需要执行的任务
         * delay：需要延迟的时间
         * unit：参数2的时间单位，是个枚举，可以是天、小时、分钟、秒、毫秒、纳秒等
         */
        scheduledExecutorService.schedule(()->{
            System.out.println(System.currentTimeMillis() + "开始执行");
            //模拟任务耗时
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "执行结束");
        },2, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

}
