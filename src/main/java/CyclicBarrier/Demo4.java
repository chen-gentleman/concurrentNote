package CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**员工5等待中，突然接了个电话，有点急事，然后就拿起筷子开吃了，其他人会怎么样呢？看着他吃
 么？


 * @Author @Chenxc
 * @Date 2022/5/17 16:06
 */
public class Demo4 {
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
//模拟倒酒，花了2秒，又得让其他9个人等2秒
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "说，不好意思，让大家久等了，给大家倒酒赔罪!");
    });

    public static class T extends Thread {
        int sleep;
        public T(String name, int sleep) {
            super(name);
            this.sleep = sleep;
        }
        @Override
        public void run() {
            long starTime = 0, endTime = 0;
            try {
//模拟休眠
                    TimeUnit.SECONDS.sleep(sleep);
                    starTime = System.currentTimeMillis();
//调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                    System.out.println(this.getName() + "到了！");
                    cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();
            System.out.println(this.getName() + ",sleep:" + this.sleep + " 等待了" + (endTime - starTime) + "(ms),开始吃饭了！");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            int sleep = 0;
            if (i == 10) {
                sleep = 10;
            }
            T t = new T("员工" + i, sleep);
            t.start();
            if (i == 5) {
                //模拟员工5接了个电话，将自己等待吃饭给打断了
                TimeUnit.SECONDS.sleep(1);
                System.out.println(t.getName() + ",有点急事，我先开干了！");
                t.interrupt();
                TimeUnit.SECONDS.sleep(2);
            }

        }
    }

}
