package countDownLatch;

import java.util.concurrent.TimeUnit;

public class Demo3 {
public static class T extends Thread {
@Override
public void run() {
while (true) {
//循环处理业务
//下面模拟阻塞代码
try {
TimeUnit.SECONDS.sleep(3);
} catch (InterruptedException e) {
    this.interrupt();
e.printStackTrace();
}
if(this.isInterrupted()){
    break;
}
}
}
}
public static void main(String[] args) throws InterruptedException {
T t = new T();
t.start();
Thread.sleep(1000);
t.interrupt();
}
}
