package ThreadLocal_InheritableThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**如何来操作Thread中的这些口袋呢，java为我们提供了一个类 ThreadLocal ，ThreadLocal对象用来操
 作Thread中的某一个口袋，可以向这个口袋中放东西、获取里面的东西、清除里面的东西，这个口袋一
 次性只能放一个东西，重复放东西会将里面已经存在的东西覆盖掉。
 * @Author @Chenxc
 * @Date 2022/5/30 10:10
 */
public class Demo3 {
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
        log("执行数据库操作");
        //模拟插入数据
        for (String s : dataList) {
            log("插入数据" + s + "成功");
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
