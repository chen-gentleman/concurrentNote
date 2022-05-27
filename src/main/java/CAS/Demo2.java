package CAS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**我们前面学了synchronized、ReentrantLock可以对资源加锁，保证并发的正确性，多线程情况下可
 以保证被锁的资源被串行访问，那么我们用synchronized来实现一下。

 * @Author @Chenxc
 * @Date 2022/5/26 9:57
 */
public class Demo2 {
    ///访问次数
    static int count = 0;

    //模拟访问一次
    public static synchronized void request()throws InterruptedException{
        //模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep(5);
        count++;
    }

    public static void main(String[] args)throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(()->{
                try {
                    for (int j = 0; j < 10; j++) {
                        request();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            });
            thread.start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" +
                (endTime - startTime) + ",count=" + count);
    }

}
