package LongAdder_LongAccumulator;

import java.util.concurrent.CountDownLatch;

/**一个jvm中实现一个计数器功能，需保证多线程情况下数据正确性。
 我们来模拟50个线程，每个线程对计数器递增100万次，最终结果应该是5000万。
 我们使用4种方式实现，看一下其性能，然后引出为什么需要使用 LongAdder 、 LongAccumulator 。
 * @Author @Chenxc
 * @Date 2022/5/31 15:50
 */

//方式一：synchronized方式实现
public class Demo1 {
    static int count = 0;

    public synchronized static void incr(){
        count++;
    }


    private static void m1()throws InterruptedException{
        long t1 = System.currentTimeMillis();
        int threadCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 1000000; j++) {
                        incr();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", count, (t2 -
                t1)));

    }

    public static void main(String[] args)throws InterruptedException {
            for (int i = 0; i < 10; i++) {
                count = 0;
                m1();
            }
        }
}
