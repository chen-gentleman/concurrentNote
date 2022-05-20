package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**SynchronousQueue ：一个不存储元素的阻塞队列，每个插入操作必须等到另外一个线程调用移除操
 作，否则插入操作一直处理阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法
 Executors.newCachedThreadPool 使用这个队列
 * @Author @Chenxc
 * @Date 2022/5/18 14:18
 */
public class Demo2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "任务" + j;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" +
                        taskName);
                //模拟任务内部处理耗时
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
