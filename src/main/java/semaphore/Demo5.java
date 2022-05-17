package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**代码中许可数量为1， semaphore.tryAcquire(1, TimeUnit.SECONDS); ：表示尝试在1秒内获取许
 可，获取成功立即返回true，超过1秒还是获取不到，返回false。线程t1获取许可成功，之后休眠了5
 秒，从输出中可以看出t2和t3都尝试了1秒，获取失败。
 * @Author @Chenxc
 * @Date 2022/5/16 15:09
 */
public class Demo5 {
   static Semaphore semaphore = new Semaphore(1);
   public static class T extends Thread {
       public T(String name) {
           super(name);
       }

       @Override
       public void run() {
           Thread thread = Thread.currentThread();
           //获取许可是否成功
           boolean acquireSuccess = false;
           try {
//尝试在1秒内获取许可，获取成功返回true，否则返回false
               System.out.println(System.currentTimeMillis() + "," +
                       thread.getName() + ",尝试获取许可,当前可用许可数量:" + semaphore.availablePermits());
               acquireSuccess = semaphore.tryAcquire(1, TimeUnit.SECONDS);
//获取成功执行业务代码
               if (acquireSuccess) {
                   System.out.println(System.currentTimeMillis() + "," +
                           thread.getName() + ",获取许可成功,当前可用许可数量:" + semaphore.availablePermits());
//休眠5秒
                   TimeUnit.SECONDS.sleep(5);
               } else {
                   System.out.println(System.currentTimeMillis() + "," +
                           thread.getName() + ",获取许可失败,当前可用许可数量:" + semaphore.availablePermits());
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           }finally {
               if(acquireSuccess){
                   semaphore.release();
               }
           }
       }
   }
    public static void main(String[] args) throws InterruptedException {
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
    }

}
