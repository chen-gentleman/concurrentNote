package threadPool;

import java.util.IdentityHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**PriorityBlockingQueue优先级队列的线程池
 * @Author @Chenxc
 * @Date 2022/5/18 14:26
 */
public class Demo3 {
    static class Task implements Runnable,Comparable<Task>{
        private int i;
        private String name;

        public Task(int i,String name){
            this.i = i;
            this.name = name;
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.i,this.i);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" +
                    this.name);
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1,
                60L,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue());
        for (int i = 0; i < 10; i++) {
            String taskName = "任务" + i;
            executor.execute(new Task(i,taskName));
        }
        for (int i = 100; i >= 90; i--) {
            String taskName = "任务" + i;
            executor.execute(new Task(i, taskName));
        }
        executor.shutdown();
    }

}
