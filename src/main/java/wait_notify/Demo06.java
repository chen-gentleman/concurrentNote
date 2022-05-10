package wait_notify;

/**
 * @Author @Chenxc
 * @Date 2022/5/10 11:23
 */
public class Demo06 {
    static Object object = new Object();
    public static class T1 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis() + ":T1 start!");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object");
                    object.wait();
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                try {
                    Thread.sleep(5000);
                    System.out.println(System.currentTimeMillis() + ":T2 start，notify one thread! ");
                    object.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T2 end!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始执行," + (Thread.currentThread().isDaemon()?"我是守护线程":"我是用户线程")); //"我是守护线程 : "我是用户线程"
        new T1().start();
        new T2().start();
    }


}
