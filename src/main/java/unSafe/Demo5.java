package unSafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**绕过构造方法创建对象
 介绍一下 allocateInstance ，这个方法可以绕过构造方法来创建对象
 * @Author @Chenxc
 * @Date 2022/5/26 16:44
 */
public class Demo5 {
    static Unsafe unsafe;
    public static class User{
        private String name;
        private int age;

        private User(){
            System.out.println("C1 default constructor!");
        }

        private User(String name, int age) {
            this.name = name;
            this.age = age;
            System.out.println("C1 有参 constructor!");
        }
    }

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
        System.out.println(unsafe.allocateInstance(User.class));
    }
}
