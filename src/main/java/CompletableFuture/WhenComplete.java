package CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @Author @Chenxc
 * @Date 2022/5/31 15:22
 */
public class WhenComplete {
    public static void whenComplete() throws Exception{
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            if (new Random().nextInt() % 2 >= 0) {
                int i = 12 / 0;
            }
            System.out.println("run end ...");
        });


        future.whenComplete((aVoid,throwable)->{
            System.out.println("执行完成！");
        });

        future.exceptionally((t)->{
            System.out.println("执行失败！"+t.getMessage());
            return null;
        });
        TimeUnit.SECONDS.sleep(2);
    }

    public static void main(String[] args) throws Exception{
        whenComplete();
    }
}
