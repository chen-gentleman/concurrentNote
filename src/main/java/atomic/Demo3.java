package atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化
 一次
 * @Author @Chenxc
 * @Date 2022/5/27 14:55
 */
public class Demo3 {
   static Demo3 demo3 = new Demo3();
    //isInit用来标注是否被初始化过

    volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Demo3.class,Boolean.class,"isInit");

   public void init()throws InterruptedException{
       if(updater.compareAndSet(demo3,Boolean.FALSE,Boolean.TRUE)){
           System.out.println(System.currentTimeMillis() + "," +
                   Thread.currentThread().getName() + "，开始初始化!");
//模拟休眠3秒
           TimeUnit.SECONDS.sleep(3);
           System.out.println(System.currentTimeMillis() + "," +
                   Thread.currentThread().getName() + "，初始化完毕!");
       }else {
        System.out.println(System.currentTimeMillis() + "," +
                Thread.currentThread().getName() + "，有其他线程已经执行了初始化!");
    }
}

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    demo3.init();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
