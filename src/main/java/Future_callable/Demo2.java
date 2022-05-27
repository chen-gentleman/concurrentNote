package Future_callable;

import java.util.concurrent.*;

/**任务执行中休眠了5秒，get方法获取执行结果，超时时间是3秒，3秒还未获取到结果，get触发了
 TimeoutException 异常，当前线程从阻塞状态苏醒了
 * @Author @Chenxc
 * @Date 2022/5/24 10:54
 */
public class Demo2 {
    public static void main(String[] args)throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> result = executorService.submit(() -> {
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName()+",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName()+",end!");
            return 10;
        });
        System.out.println(System.currentTimeMillis() + "," +
                Thread.currentThread().getName());
        try {
            System.out.println(System.currentTimeMillis() + "," +
                    Thread.currentThread().getName() + ",结果：" + result.get(3,TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
