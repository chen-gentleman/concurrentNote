package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**自定义饱和策略
 需要实现 RejectedExecutionHandler 接口。任务无法处理的时候，我们想记录一下日志，我们需要
 自定义一个饱和策略
 * @Author @Chenxc
 * @Date 2022/5/20 9:24
 */
public class Demo5 {
    public static class Task implements Runnable{
        private String name;
        public Task(String name){
            this.name= name;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理"+this.name);
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,60,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1), Executors.defaultThreadFactory(),
                (r,exe)->{
                    //自定义饱和策略
                    //记录一下无法处理的任务
                    System.out.println("无法处理的任务：" + r.toString());
                });
        for (int i = 0; i < 5; i++) {
            executor.execute(new Task("任务-"+i));
        }
        executor.shutdown();
    }
}
