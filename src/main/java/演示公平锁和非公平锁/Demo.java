package 演示公平锁和非公平锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author @Chenxc
 * @Date 2022/5/31 16:16
 */

public class Demo {

    public static void test(boolean fair)throws InterruptedException{
        ReentrantLock lock = new ReentrantLock(fair);

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("start");
                TimeUnit.SECONDS.sleep(3);
                new Thread(() -> {
                    m1(lock, "son");
                }).start();
                System.out.println("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.setName("t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        m1(lock,"father");

    }

    public static void m1(ReentrantLock lock,String threadPre){
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"-获取到锁!");
                } finally {
                    lock.unlock();
                }
            });
            thread.setName(threadPre + "-" + i);
            thread.start();
        }

    }

    public static void main(String[] args) throws InterruptedException {
//非公平锁
        test(false);
        TimeUnit.SECONDS.sleep(4);
        System.out.println("------------------------------");
//公平锁
        test(true);
    }

}
