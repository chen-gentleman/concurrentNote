package ThreadLocal_InheritableThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**看一下上面的输出，有些traceId为null，这是为什么呢？这是因为dao中为了提升处理速度，创建了子
 线程来并行处理，子线程调用log的时候，去自己的存放traceId的口袋中拿去东西，肯定是空的了。
 那有什么办法么？可不可以这样？
 父线程相当于主管，子线程相当于干活的小弟，主管让小弟们干活的时候，将自己兜里面的东西复制一
 份给小弟们使用，主管兜里面可能有很多牛逼的工具，为了提升小弟们的工作效率，给小弟们都复制一
 个，丢到小弟们的兜里，然后小弟就可以从自己的兜里拿去这些东西使用了，也可以清空自己兜里面的
 东西。
 inheritableThreadLocals相当于线程中另外一种兜，这种兜有什么特征呢，当创建子线程的时候，子线
 程会将父线程这种类型兜的东西全部复制一份放到自己的 inheritableThreadLocals 兜中，使用
 InheritableThreadLocal 对象可以操作线程中的 inheritableThreadLocals 兜。

 * @Author @Chenxc
 * @Date 2022/5/30 10:10
 */
public class Demo5 {
    //创建一个操作Thread中存放请求任务追踪id口袋的对象,子线程可以继承父线程中内容
    static InheritableThreadLocal<String> traceIdKD = new InheritableThreadLocal<>();

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
