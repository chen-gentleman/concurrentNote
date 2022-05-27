package unSafe;

import org.omg.CORBA.INITIALIZE;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author @Chenxc
 * @Date 2022/5/26 11:40
 */
public class Demo2 {
    static Unsafe unsafe;
    ///访问次数
    static int count;
    //count在Demo.class对象中的地址偏移量
    static long countOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            Field count1 = Demo2.class.getDeclaredField("count");
            //获取count字段在Demo2中的内存地址的偏移量
            countOffset = unsafe.staticFieldOffset(count1);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void request()throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        unsafe.getAndAddInt(Demo2.class,countOffset,1);
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
