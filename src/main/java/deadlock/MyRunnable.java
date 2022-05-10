package deadlock;

/**
 * @Author @Chenxc
 * @Date 2022/4/12 17:23
 */
public class MyRunnable implements Runnable {
    Object obj1;
    Object obj2;
    int a;
    int b;
    boolean flag;

    public MyRunnable(Object obj1, Object obj2, int a, int b, boolean flag) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.a = a;
        this.b = b;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            if(flag){
                synchronized (obj1){
                    Thread.sleep(100);
                    synchronized (obj2){
                        System.out.println(a+b);
                    }
                }
            }else{
                synchronized (obj2){
                    Thread.sleep(100);
                    synchronized (obj1){
                        System.out.println(a+b);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
