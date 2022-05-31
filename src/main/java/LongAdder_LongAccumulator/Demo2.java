package LongAdder_LongAccumulator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**个jvm中实现一个计数器功能，需保证多线程情况下数据正确性。
 我们来模拟50个线程，每个线程对计数器递增100万次，最终结果应该是5000万。
 我们使用4种方式实现，看一下其性能，然后引出为什么需要使用 LongAdder 、 LongAccumulator 。
 * @Author @Chenxc
 * @Date 2022/5/31 15:57
 */
//方式2：AtomicLong实现
    //AtomicLong 内部采用CAS的方式实现，并发量大的情况下，CAS失败率比较高，导致性能比
//synchronized还低一些。并发量不是太大的情况下，CAS性能还是可以的
public class Demo2 {
    static AtomicLong count = new AtomicLong(0);

    public static void incr(){
        count.incrementAndGet();
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

    public static void main(String[] args)throws Exception {
        for (int i = 0; i < 10; i++) {
            count.set(0);
            m1();
        }
    }

}
