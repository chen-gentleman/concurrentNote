package ThreadLocal_InheritableThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**目前java开发web系统一般有3层，controller、service、dao，请求到达controller，controller调用
 service，service调用dao，然后进行处理。
 我们写一个简单的例子，有3个方法分别模拟controller、service、dao
 * @Author @Chenxc
 * @Date 2022/5/27 15:31
 */
public class Demo1 {
    static AtomicInteger threadIndex = new AtomicInteger(1);
    //创建处理请求的线程池子
    static ThreadPoolExecutor disposeRequestExecutor = new ThreadPoolExecutor(
            3,
            3,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            (r)->{
                Thread thread = new Thread(r);
                thread.setName("disposeRequestThread-" + threadIndex.getAndIncrement());
                return thread;
            }
    );


    //记录日志
    public static void log(String msg){
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        System.out.println("****" + System.currentTimeMillis() + ",[线程:" +
                Thread.currentThread().getName() + "]," + stack[1] + ":" + msg);
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
            disposeRequestExecutor.execute(()->{
                controller(dataList);
            });
        }
        disposeRequestExecutor.shutdown();
    }

}
