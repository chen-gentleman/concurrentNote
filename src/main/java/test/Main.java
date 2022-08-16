package test;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author @Chenxc
 * @Date 2022/5/20 9:56
 */
public class Main {
    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        System.out.println(now);
        now.set(Calendar.HOUR_OF_DAY,23);
        now.set(Calendar.MINUTE,55);
        System.out.println(now);
        System.out.println(now.getTime());

        System.out.println(new Date(Long.MAX_VALUE));

        String t = "fnsdjkfn\\dfjknnlk\\";
        System.out.println(t);
        System.out.println(t.replaceAll("\\\\","/"));

//        Persion student = new Student();
//        student.say("cxc");
//        System.out.println(student.getClass().getClassLoader());
    }

    public static int get(String[] words) {
        if (null == words || words.length <= 0) {
            return 0;
        }
        int result = 0;
        int length = words.length;
        String str1 = "";
        String str2 = "";

        for (int i = 0; i < length; i++) {
            str1 = words[i];

            for (int j = (i + 1); j < length; j++) {
                str2 = words[j];
                char[] arr = str2.toCharArray();
                int arrlength = arr.length;
                boolean haveWord = false;
                for (int k = 0; k < arrlength; k++) {
                    if (str1.contains(arr[k] + "")) {
                        break;
                    }
                    haveWord = true;
                }
                if (haveWord) {
                    int tempResult = str1.length() * str2.length();
                    if (tempResult > result) {
                        result = tempResult;
                    }
                }
            }
        }
        return result;
    }

}
