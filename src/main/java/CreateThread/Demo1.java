package CreateThread;

import java.util.concurrent.TimeUnit;

/**
 * @Author @Chenxc
 * @Date 2022/4/22 17:40
 */
public class Demo1 {
    public static void main(String[] args) throws Exception{
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println(1);
                }
            }
        };
        thread1.setName("thread1");
        thread1.start();//当前线程休眠1秒
        TimeUnit.SECONDS.sleep(1);
//关闭线程thread1
        thread1.stop();
//输出线程thread1的状态
        System.out.println(thread1.getState());
//当前线程休眠1秒
        TimeUnit.SECONDS.sleep(1);
//输出线程thread1的状态
        System.out.println(thread1.getState());
    }
}
