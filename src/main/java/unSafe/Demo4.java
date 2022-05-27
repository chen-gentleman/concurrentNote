package unSafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author @Chenxc
 * @Date 2022/5/26 16:44
 */
public class Demo4 {
    static Unsafe unsafe;
    public static class User{
        private String name;
        private int age;
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
        User user = new User();
        user.name = "chen";
        user.age = 23;

        Field field = user.getClass().getDeclaredField("name");
        //返回对象成员属性在内存地址相对于此对象的内存地址的偏移量
        long name_offset = unsafe.objectFieldOffset(field);
        System.out.println("name_offset="+name_offset);
        //获得给定对象的指定地址偏移量的值，与此类似操作还有：getInt，getDouble，getLong，getChar等
        Object object = unsafe.getObject(user, name_offset);
        System.out.println(object);

        //获得给定对象的指定地址偏移量的值，与此类似操作还有：getInt，getDouble，getLong，getChar等
        unsafe.putObject(user,name_offset,"sdfsf");
        System.out.println(user.name);

    }
}
