package Future_callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**代码中创建了一个线程池，调用线程池的 submit 方法执行任务，submit参数为 Callable 接口：表示
 需要执行的任务有返回值，submit方法返回一个 Future 对象，Future相当于一个凭证，可以在任意时
 间拿着这个凭证去获取对应任务的执行结果（调用其 get 方法），代码中调用了 result.get() 方法之
 后，此方法会阻塞当前线程直到任务执行结束。
 * @Author @Chenxc
 * @Date 2022/5/24 10:47
 */
public class Demo1 {
    public static void main(String[] args)throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> result = executorService.submit(() -> {
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName() + ",start!");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName() + ",end!");
            return 10;
        });
        System.out.println(System.currentTimeMillis() + "," +
                Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis() + "," +
                Thread.currentThread().getName() + ",结果：" + result.get());
        executorService.shutdown();
    }
}
