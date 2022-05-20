package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**虽然jdk提供了 ThreadPoolExecutor 这个高性能线程池，但是如果我们自己想在这个线程池上面做一
 些扩展，比如，监控每个任务执行的开始时间，结束时间，或者一些其他自定义的功能，我们应该怎么
 办？
 这个jdk已经帮我们想到了， ThreadPoolExecutor 内部提供了几个方法 beforeExecute 、
 afterExecute 、 terminated ，可以由开发人员自己去这些方法
 * @Author @Chenxc
 * @Date 2022/5/20 9:43
 */
public class Demo6 {
    static class Task implements Runnable {
        String name;
        public Task(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" +
                    this.name);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
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

    public static void main(String[] args)throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1,
                60l,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                Executors.defaultThreadFactory(),
                (r,exe)->{
                    //自定义饱和策略
                    //记录一下无法处理的任务
                    System.out.println("无法处理的任务：" + r.toString());
                    })
        {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(System.currentTimeMillis() + "," +
                        t.getName() + ",开始执行任务:" + r.toString());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(System.currentTimeMillis() + "," +
                        Thread.currentThread().getName() + ",任务:" + r.toString() + "，执行完毕!");
            }


            @Override
            protected void terminated() {
                System.out.println(System.currentTimeMillis() + "," +
                        Thread.currentThread().getName() + "，关闭线程池!");
            }
        };


        for (int i = 0; i < 10; i++) {
            executor.execute(new Task("任务-" + i));
        }
        TimeUnit.SECONDS.sleep(1);
        executor.shutdown();
    }



}


