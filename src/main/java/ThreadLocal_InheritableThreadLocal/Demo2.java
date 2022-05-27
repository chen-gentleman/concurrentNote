package ThreadLocal_InheritableThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**上面的请求采用线程池的方式处理的，多个请求可能会被一个线程处理，通过日志很难看出那些日志是
 同一个请求，我们能不能给请求加一个唯一标志，日志中输出这个唯一标志，当然可以。
 如果我们的代码就只有上面示例这么简单，我想还是很容易的，上面就3个方法，给每个方法加个
 traceId参数，log方法也加个traceId参数，就解决了
 * @Author @Chenxc
 * @Date 2022/5/27 15:31
 */
public class Demo2 {
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
    public static void log(String msg,String traceId){
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        System.out.println("****" + System.currentTimeMillis() + ",[线程:" +
                Thread.currentThread().getName() + "],"+",[traceId"+ traceId +"]"+ stack[1] + ":" + msg);
    }

    public static void controller(List<String> dataList,String traceId){
        log("接受请求",traceId);
        service(dataList,traceId);
    }

    public static void service(List<String> dataList,String traceId){
        log("执行业务",traceId);
        dao(dataList,traceId);
    }

    public static void dao(List<String> dataList,String traceId){
        log("执行数据库操作",traceId);
        //模拟插入数据
        for (String s : dataList) {
            log("插入数据" + s + "成功",traceId);
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
                controller(dataList,traceId);
            });
        }
        disposeRequestExecutor.shutdown();
    }

}
