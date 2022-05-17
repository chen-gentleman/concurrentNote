package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * public void acquire() throws InterruptedException
 * 这个方法会响应线程中断，主线程中对t2、t3发送中断信号之后， acquire() 方法会触发
 * InterruptedException 异常，t2、t3最终没有获取到许可，但是他们都执行了finally中的释放许可的
 * 操作，最后导致许可数量变为了2，导致许可数量增加了。所以程序中释放许可的方式有问题。需要改
 * 进一下，获取许可成功才去释放锁
 * @Author @Chenxc
 * @Date 2022/5/16 14:57
 */
public class Demo3 {
    static Semaphore semaphore = new Semaphore(1);

    public static class T extends Thread{
        public T(String name){
            super(name);
        }

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            try{
                semaphore.acquire();
                System.out.println(System.currentTimeMillis() + "," +
                        thread.getName() + ",获取许可,当前可用许可数量:" + semaphore.availablePermits());
//休眠100秒
                TimeUnit.SECONDS.sleep(100);
                System.out.println(System.currentTimeMillis() + "," +
                        thread.getName() + ",运行结束!");
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
            System.out.println(System.currentTimeMillis() + "," +
                    thread.getName() + ",当前可用许可数量:" + semaphore.availablePermits());
        }
    }

    public static void main(String[] args) throws InterruptedException{
        T t1 = new T("t1");
        t1.start();
        //休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t2 = new T("t2");
        t2.start();
//休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t3 = new T("t3");
        t3.start();
//给t2和t3发送中断信号
        t2.interrupt();
        t3.interrupt();

    }
}
