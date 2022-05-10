package join_yeild;

import java.util.IdentityHashMap;

/**
 * @Author @Chenxc
 * @Date 2022/5/10 11:39
 */
public class Demo08 {
    static int num = 0;

    public static class T1 extends Thread {
        public T1(String name){
            super(name);
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ",start " +
                    this.getName());
            for (int i = 0; i < 10; i++) {
                num++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() + ",end " +
                    this.getName());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        t1.start();
        t1.join();
        System.out.println(System.currentTimeMillis() + ",num = " + num);
    }


}
