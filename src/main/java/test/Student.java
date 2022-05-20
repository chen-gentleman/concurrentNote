package test;

/**
 * @Author @Chenxc
 * @Date 2022/5/20 9:55
 */
public class Student extends Persion {
    @Override
    public void say(String name) {
        System.out.println("你好，"+name+",我是子类方法");
    }
}
