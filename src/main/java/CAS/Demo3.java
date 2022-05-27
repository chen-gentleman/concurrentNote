package CAS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**方式2中我们通过加锁的方式让上面3步骤同时只能被一个线程操作，从而保证结果的正确性。
 我们是否可以只在第3步加锁，减少加锁的范围

 * @Author @Chenxc
 * @Date 2022/5/26 9:57
 */
public class Demo3 {
    ///访问次数
    volatile static int count = 0;

    //模拟访问一次
    public static void request()throws InterruptedException{
        //模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep(5);
        //count++;
        int expectCount;
        do{
            expectCount = getCount();
        }while (!compareAndSwap(expectCount,expectCount + 1));
    }


    /**
     * 获取count当前的值
     *
     * @return
     */
    public static int getCount() {
        return count;
    }
    /**
     * @param expectCount 期望count的值
     * @param newCount 需要给count赋的新值
     * @return
     */
    public static synchronized boolean compareAndSwap(int expectCount, int newCount) {
//判断count当前值是否和期望的expectCount一样，如果一样将newCount赋值给count
        if (getCount() == expectCount) {
            count = newCount;
            return true;
        }
        return false;
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
