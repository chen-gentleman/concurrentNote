package threadPool;

import java.util.concurrent.*;

/**线程池使用的简单示例
 * @Author @Chenxc
 * @Date 2022/5/18 12:50
 */
public class Demo1 {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(3,//核心线程数：3
            5,//最大线程数：5
            10,//空闲时间:10
            TimeUnit.SECONDS,//空闲时间单位:秒
            new ArrayBlockingQueue<Runnable>(10),//任务队列：10
            Executors.defaultThreadFactory(),//线程工厂
            new ThreadPoolExecutor.AbortPolicy());//饱和策略
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + taskName +
                        "处理完毕");
            });
        }
        executor.shutdown();//关闭线程池
    }
}
