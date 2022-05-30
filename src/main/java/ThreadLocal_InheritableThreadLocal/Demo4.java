package ThreadLocal_InheritableThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**继续上面的实例，dao中循环处理dataList的内容，假如dataList处理比较耗时，我们想加快处理速度有
 什么办法么？大家已经想到了，用多线程并行处理 dataList ，那么我们把代码改一下，如下：

 * @Author @Chenxc
 * @Date 2022/5/30 10:10
 */
public class Demo4 {
    //创建一个操作Thread中存放请求任务追踪id口袋的对象
    static ThreadLocal<String> traceIdKD = new ThreadLocal<>();

    static AtomicInteger threadIndex = new AtomicInteger(1);

    static ThreadPoolExecutor disposeRequestExecutor = new ThreadPoolExecutor(
            3,
            3,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r->{
                Thread thread = new Thread(r);
                thread.setName("disposeRequestThread-" + threadIndex.getAndIncrement());
                return thread;
            }
    );

    //记录日志
    public static void log(String msg){
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        //获取当前线程存放tranceId口袋中的内容
        String traceId = traceIdKD.get();
        System.out.println("****" + System.currentTimeMillis() + ",[线程:" +
                Thread.currentThread().getName() + "],"+",==[traceId"+ traceId +"]=="+ stack[1] + ":" + msg);

    }

    public static void controller(List<String> dataList){
        log("接受请求");
        service(dataList);
    }

    public static void service(List<String> dataList){
        log("执行业务");
        dao(dataList);
    }

    public static void dao(List<String> dataList){
        CountDownLatch countDownLatch = new CountDownLatch(dataList.size());
        log("执行数据库操作");
        String threadName = Thread.currentThread().getName();
        //模拟插入数据
        for (String s : dataList) {
            new Thread(()->{
               try{
                   TimeUnit.MILLISECONDS.sleep(100);
                   log("插入数据" + s + "成功,主线程：" + threadName);
               }catch (InterruptedException e){
                   e.printStackTrace();
               }finally {
                   countDownLatch.countDown();
               }
            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println("threadName="+threadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //需要插入的数据
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add("数据" + i);
        }
        int requestCount = 5;

        for (int i = 0; i < requestCount; i++) {
            String traceId = i+"";
            disposeRequestExecutor.execute(()->{
                traceIdKD.set(traceId);
                try {
                    controller(dataList);
                } finally {
                    traceIdKD.remove();
                }
            });
        }
        disposeRequestExecutor.shutdown();
    }


}
