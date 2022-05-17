package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**上面程序运行后一直无法结束，观察一下代码，代码中获取许可后，没有释放许可的代码，最终导致，
 可用许可数量为0，其他线程无法获取许可，会在 semaphore.acquire(); 处等待，导致程序无法结
 束。
 * @Author @Chenxc
 * @Date 2022/5/16 14:49
 */
public class Demo2 {
    static Semaphore semaphore = new Semaphore(2);
    public static class T extends Thread{
        public T(String name){
            super(name);
        }

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            try{
                semaphore.acquire();
                System.out.println(System.currentTimeMillis() + ","+thread.getName()+",获得许可！");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(System.currentTimeMillis() + "," +
                        thread.getName() + ",运行结束!");
                System.out.println(System.currentTimeMillis() + "," +
                        thread.getName() + ",当前可用许可数量:" + semaphore.availablePermits());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new T("t_" + i).start();
        }
    }
}
