package Executor2;

import java.util.concurrent.*;

/**还是举个例子说明更好理解一些。
 买新房了，然后在网上下单买冰箱、洗衣机，电器商家不同，所以送货耗时不一样，然后等他们送货，
 快递只愿送到楼下，然后我们自己将其搬到楼上的家中。
 用程序来模拟上面的实现
 * @Author @Chenxc
 * @Date 2022/5/24 11:22
 */
public class Demo1 {
    static class GoodsModel{
        String name;
        //购物开始时间
        long startTime;
        //送到的时间
        long endTime;

        public GoodsModel(String name, long startTime, long endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }
        @Override
        public String toString() {
            return name + "，下单时间[" + this.startTime + "," + this.endTime + "]，耗 时:" + (this.endTime - this.startTime);
        }
    }

    /**
     * 将商品搬上楼
     * @param goodsModel
     * @throws InterruptedException
     */
    static void moveUp(GoodsModel goodsModel)throws InterruptedException{
        TimeUnit.SECONDS.sleep(5);
        System.out.println("将商品搬上楼，商品信息:" + goodsModel);
    }

    /**
     *模拟下单
     * @param name 商品名称
     * @param costTime  耗时
     * @return
     */
    static Callable<GoodsModel> buyGoods(String name,long costTime){
        return ()->{
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "购买" + name + "下单!");
            //模拟送货耗时
            TimeUnit.SECONDS.sleep(costTime);
            long endTime = System.currentTimeMillis();
            System.out.println(endTime+ name+"送到了!");
            return new GoodsModel(name,startTime,endTime);
        };
    }

    public static void main(String[] args) throws Exception{
        long st = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<GoodsModel> bxFuture = executorService.submit(buyGoods("冰箱", 5));
        Future<GoodsModel> xyjFuture = executorService.submit(buyGoods("洗衣机", 2));
        executorService.shutdown();

        GoodsModel xyj = xyjFuture.get();
        moveUp(xyj);

        GoodsModel bxGoodsModel = bxFuture.get();
        moveUp(bxGoodsModel);

        long et = System.currentTimeMillis();
        System.out.println(et+"货物已送到家里咯，哈哈哈！");
        System.out.println("总耗时:" + (et - st));
    }

}
