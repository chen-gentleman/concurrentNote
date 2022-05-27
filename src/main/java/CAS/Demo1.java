package CAS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**需求：我们开发了一个网站，需要对访问量进行统计，用户每次发一次请求，访问量+1，如何实现呢？
 下面我们来模仿有100个人同时访问，并且每个人对咱们的网站发起10次请求，最后总访问次数应该是
 1000次。实现访问如下
 * @Author @Chenxc
 * @Date 2022/5/26 9:57
 */
public class Demo1 {
    ///访问次数
    static int count = 0;

    //模拟访问一次
    public static void request()throws InterruptedException{
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
