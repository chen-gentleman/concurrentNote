package queue.DelayQueue;

import queue.SynchronousQueue.Demo;

import java.util.Calendar;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author @Chenxc
 * @Date 2022/5/31 10:09
 */
public class demo {
    static class Msg implements Delayed {
        //优先级，越小优先级越高
        private int priority;
        //推送的信息
        private String msg;
        //定时发送时间，毫秒格式
        private long sendTimeMs;


        public Msg(int priority, String msg, long sendTimeMs) {
            this.priority = priority;
            this.msg = msg;
            this.sendTimeMs = sendTimeMs;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.sendTimeMs -
                    Calendar.getInstance().getTimeInMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if(o instanceof Msg){
                Msg c2 = (Msg) o;
                return Integer.compare(this.priority,c2.priority);
            }
            return 0;
        }


        @Override
        public String toString() {
            return "Msg{" +
                    "priority=" + priority +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
    //推送队列
    static DelayQueue<Msg> pushQueue = new DelayQueue<Msg>();
    static {
//启动一个线程做真实推送
        new Thread(() -> {
            while (true) {
                Msg msg;
                try {
//获取一条推送消息，此方法会进行阻塞，直到返回结果
                    msg = pushQueue.take();
//此处可以做真实推送
                    long endTime = System.currentTimeMillis();
                    System.out.println(String.format("定时发送时间：%s,实际发送时间：%s,发送消息:%s", msg.sendTimeMs, endTime, msg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //推送消息，需要发送推送消息的调用该方法，会将推送信息先加入推送队列
    public static void pushMsg(int priority, String msg, long sendTimeMs) throws
            InterruptedException {
        pushQueue.put(new Msg(priority, msg, sendTimeMs));
    }



    public static void main(String[] args) throws InterruptedException {
        for (int i = 5; i >= 1; i--) {
            String msg = "一起来学java高并发,第" + i + "天";
            pushMsg(i, msg, Calendar.getInstance().getTimeInMillis() + i * 2000);
        }
    }


}
