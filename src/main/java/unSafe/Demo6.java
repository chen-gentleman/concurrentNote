package unSafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**数组相关的一些方法
 这部分主要介绍与数据操作相关的arrayBaseOffset与arrayIndexScale这两个方法，两者配合起来使
 用，即可定位数组中每个元素在内存中的位置。
 * @Author @Chenxc
 * @Date 2022/5/26 16:44
 */
public class Demo6 {
    static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)throws Exception {
        Integer[] words = {1,2,3};
        System.out.println(words.getClass());
        //返回数组中第一个元素的偏移地址
        int arrayBaseOffset = unsafe.arrayBaseOffset(words.getClass());
        //返回数组中一个元素占用的大小
        int arrayIndexScale = unsafe.arrayIndexScale(words.getClass());

        System.out.println(arrayBaseOffset);
        System.out.println(arrayIndexScale);
        System.out.println("a".getBytes().length);
        System.out.println(int.class);
    }
}
