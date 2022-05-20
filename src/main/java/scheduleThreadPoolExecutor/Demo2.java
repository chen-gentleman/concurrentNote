package scheduleThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**scheduleAtFixedRate:固定的频率执行任务
 * 使用 ScheduleThreadPoolExecutor的scheduleAtFixedRate 方法，该方法设置了执行周期，下一次
 * 执行时间相当于是上一次的执行时间加上period，任务每次执行完毕之后才会计算下次的执行时间。
 * @Author @Chenxc
 * @Date 2022/5/20 10:48
 */
public class Demo2 {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        AtomicInteger count = new AtomicInteger(1);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        /**
         * public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
         * long initialDelay,
         * long period,
         * TimeUnit unit);
         * 4个参数：
         * command：表示要执行的任务
         * initialDelay：表示延迟多久执行第一次
         * period：连续执行之间的时间间隔
         * unit：参数2和参数3的时间单位，是个枚举，可以是天、小时、分钟、秒、毫秒、纳秒等
         */
        scheduledExecutorService.scheduleAtFixedRate(()->{
            int currCount = count.getAndIncrement();
            System.out.println(Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + "第" + currCount +
                    "次" + "开始执行");
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "第" + currCount +
                    "次" + "执行结束");
        },1,1, TimeUnit.SECONDS);
        //scheduledExecutorService.shutdown();
    }
}
