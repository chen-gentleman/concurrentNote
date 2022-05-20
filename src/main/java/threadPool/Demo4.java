package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**自定义创建线程的工厂
 * 给线程池中线程起一个有意义的名字，在系统出现问题的时候，通过线程堆栈信息可以更容易发现系统
 * 中问题所在。自定义创建工厂需要实现 java.util.concurrent.ThreadFactory 接口中的 Thread
 * newThread(Runnable r) 方法，参数为传入的任务，需要返回一个工作线程。
 * @Author @Chenxc
 * @Date 2022/5/18 17:29
 */
public class Demo4 {

    static AtomicInteger threadNum = new AtomicInteger(1);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 60l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
                (r) -> {
                    Thread thread = new Thread(r);
                    thread.setName("自定义线程-" + threadNum.getAndIncrement());
                    return thread;
                }

        );

        for (int i = 0; i < 5; i++) {
            String taskName = "任务-" + i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" +
                        taskName);
            });
        }
        executor.shutdown();

    }



}
