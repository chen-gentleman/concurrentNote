package queue.PriorityBlockingQueue;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**还是上面的推送业务，目前推送是按照放入的先后顺序进行发送的，比如有些公告比较紧急，优
 先级比较高，需要快点发送，怎么搞？此时 PriorityBlockingQueue 就派上用场了
 优先级队列放入元素的时候，会进行排序，所以我们需要指定排序规则，有2种方式：
 1. 创建 PriorityBlockingQueue 指定比较器 Comparator
 2. 放入的元素需要实现 Comparable 接口
 * @Author @Chenxc
 * @Date 2022/5/31 9:30
 */
public class Demo1 {
    static class Msg implements Comparable<Msg>{
        //优先级，越小优先级越高
        private int priority;
        private String msg;

        public Msg(int priority, String msg) {
            this.priority = priority;
            this.msg = msg;
        }

        @Override
        public int compareTo(Msg o) {
            return Integer.compare(this.priority,o.priority);
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "priority=" + priority +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }


    static PriorityBlockingQueue<Msg> pushQueue = new PriorityBlockingQueue<>();


    static {
        new Thread(()->{
            Msg msg;
            while (true){
                try{
                    long starTime = System.currentTimeMillis();
                    //获取一条推送消息，此方法会进行阻塞，直到返回结果
                    msg = pushQueue.take();
                    //模拟推送耗时
                    TimeUnit.MILLISECONDS.sleep(100);
                    long endTime = System.currentTimeMillis();
                    System.out.println(String.format("[%s,%s,take耗时:%s],%s,发送消息:%s", starTime, endTime, (endTime - starTime),
                    Thread.currentThread().getName(), msg));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //推送消息，需要发送推送消息的调用该方法，会将推送信息先加入推送队列
    public static void pushMsg(int priority, String msg) throws InterruptedException {
        pushQueue.put(new Msg(priority, msg));
    }

    public static void main(String[] args) throws InterruptedException{
        for (int i = 5; i >= 1; i--) {
            String msg = "一起来学java高并发,第" + i + "天";
            Demo1.pushMsg(i, msg);
        }
    }

}
