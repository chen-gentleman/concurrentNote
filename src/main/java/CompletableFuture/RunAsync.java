package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**runAsync方法不支持返回值。
 supplyAsync可以支持返回值。
 * @Author @Chenxc
 * @Date 2022/5/31 15:07
 */
public class RunAsync {

    //无返回值
    public static void runAsync()throws Exception{
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run end...");
        });
        future.get();
    }

    //有返回值
    public static void supplyAsync()throws Exception{
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            System.out.println("run end ...");
            return System.currentTimeMillis();
        });

        Long time = future.get();
        System.out.println("time= " + time);
    }

    public static void main(String[] args) throws Exception{
        runAsync();
        supplyAsync();
    }
}
