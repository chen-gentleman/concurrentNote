package unSafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**通过反射获取Unsafe实例
 * Unsafe类为单例实现，提供静态方法getUnsafe获取Unsafe实例，内部会判断当前
 * 调用者是否是由系统类加载器加载的，如果不是系统类加载器加载的，会抛出 SecurityException 异
 * 常。
 * 那我们想使用这个类，如何获取呢？
 * 可以把我们的类放在jdk的lib目录下，那么启动的时候会自动加载，这种方式不是很好。
 * 我们学过反射，通过反射可以获取到 Unsafe 中的 theUnsafe 字段的值，这样可以获取到Unsafe对象
 * 的实例。
 * @Author @Chenxc
 * @Date 2022/5/26 11:15
 */
public class Demo1 {
    static Unsafe unsafe;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe)theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(unsafe);
    }
}
