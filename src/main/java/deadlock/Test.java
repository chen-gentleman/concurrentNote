package deadlock;

/**
 * @Author @Chenxc
 * @Date 2022/4/12 17:28
 */
public class Test {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        MyRunnable myRunnable1 = new MyRunnable(obj1,obj2,10,20,true);
        MyRunnable myRunnable2 = new MyRunnable(obj1,obj2,1,2,false);

        Thread thread1 = new Thread(myRunnable1);
        thread1.setName("thread1");
        thread1.start();

        Thread thread2 = new Thread(myRunnable2);
        thread2.setName("thread2");
        thread2.start();
    }
}
