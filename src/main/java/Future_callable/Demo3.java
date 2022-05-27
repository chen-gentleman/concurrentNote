package Future_callable;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**FutureTask除了实现Future接口，还实现了Runnable接口，因此FutureTask可以交给Executor执行，
 也可以交给线程执行执行（Thread有个Runnable的构造方法），FutureTask表示带返回值结果的任
 务。
 上面我们演示的是通过线程池执行任务然后获取执行结果。
 这次我们通过FutureTask类，自己启动一个线程来获取执行结果
 * @Author @Chenxc
 * @Date 2022/5/24 11:09
 */
public class Demo3 {
    public static void main(String[] args)throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(()->{System.out.println(System.currentTimeMillis() + "," +
                Thread.currentThread().getName()+",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName()+",end!");
            return 10;
        });

        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName());
        new Thread(futureTask).start();
        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName()+",结果:"+futureTask.get());


    }
}
