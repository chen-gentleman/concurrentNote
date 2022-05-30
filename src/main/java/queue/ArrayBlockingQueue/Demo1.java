package queue.ArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**需求：业务系统中有很多地方需要推送通知，由于需要推送的数据太多，我们将需要推送的信息先丢到
 阻塞队列中，然后开一个线程进行处理真实发送，代码如下：
 * @Author @Chenxc
 * @Date 2022/5/30 16:07
 */
public class Demo1 {
    //推送队列
    static ArrayBlockingQueue<String> pushQueue = new ArrayBlockingQueue<>(10000);

    static {
        new Thread(()->{
            while (true){
                String msg;
                try{
                    long starTime = System.currentTimeMillis();
                    //获取一条推送消息，此方法会进行阻塞，直到返回结果
                    msg = pushQueue.take();
                    long endTime = System.currentTimeMillis();
                    //模拟推送耗时
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println(String.format("[%s,%s,take耗时:%s],%s,发送 消息:%s", starTime, endTime, (endTime - starTime),
                    Thread.currentThread().getName(), msg));

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static void pushMsg(String msg)throws InterruptedException{
        pushQueue.put(msg);
    }

    public static void main(String[] args) throws InterruptedException{
        for (int i = 0; i <= 5; i++) {
            String msg = "一起来学java高并发,第" + i + "天";
            //模拟耗时
            TimeUnit.SECONDS.sleep(i);
            Demo1.pushMsg(msg);
        }
    }

}
