package LongAdder_LongAccumulator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;

/**方式3：LongAdder实现
 * @Author @Chenxc
 * @Date 2022/5/31 16:02
 */
public class Demo3 {
    static LongAdder count = new LongAdder();

    public static void incr(){
        count.increment();
    }

    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        for (int i = 0; i < 10; i++) {
            count.reset();//reset() 方法可以重置 LongAdder 的值，使其归0。
            m1();
        }
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

}
