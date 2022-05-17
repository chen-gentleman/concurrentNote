package CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**公司组织旅游，大家都有经历过，10个人，中午到饭点了，需要等到10个人都到了才能开饭，先到的人
 坐那等着，代码如下：
 * @Author @Chenxc
 * @Date 2022/5/17 15:43
 */
public class Demo1 {
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public static class T extends Thread{
        int sleep;
        public T(String name,int sleep){
            super(name);
            this.sleep = sleep;
        }

        @Override
        public void run() {
           try{
               TimeUnit.SECONDS.sleep(sleep);
               long startTime = System.currentTimeMillis();
               cyclicBarrier.await();
               long endTime = System.currentTimeMillis();
               System.out.println(this.getName() + ",sleep:" + this.sleep + "等待了" + (endTime - startTime) + "(ms),开始吃饭了！");

           }catch (InterruptedException e){
               e.printStackTrace();
           }catch (BrokenBarrierException e){
               e.printStackTrace();
           }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new T("员工" + i ,i).start();
        }
    }
}
