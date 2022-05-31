package queue.SynchronousQueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**同步阻塞队列，SynchronousQueue没有容量，与其他BlockingQueue不同，
 SynchronousQueue是一个不存储元素的BlockingQueue，每一个put操作必须要等待一个take
 操作，否则不能继续添加元素，反之亦然。SynchronousQueue 在现实中用的不多，线程池中有
 用到过， Executors.newCachedThreadPool() 实现中用到了这个队列，当有任务丢入线程池的
 时候，如果已创建的工作线程都在忙于处理任务，则会新建一个线程来处理丢入队列的任务。
 * @Author @Chenxc
 * @Date 2022/5/31 9:44
 */
public class Demo {
    static SynchronousQueue<String> queue = new SynchronousQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                long starTime = System.currentTimeMillis();
                queue.put("java高并发系列，路人甲Java!");
                long endTime = System.currentTimeMillis();
                System.out.println(String.format("[%s,%s,take耗时:%s],%s",
                        starTime, endTime, (endTime - starTime), Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
//休眠5秒之后，从队列中take一个元素
        TimeUnit.SECONDS.sleep(5);
        System.out.println(System.currentTimeMillis() + "调用take获取并移除元素," +
                queue.take());
    }
}
