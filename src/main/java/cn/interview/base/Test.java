package cn.interview.base;

import java.lang.reflect.Field;

/**
 * @author hongzhou.wei
 * @date 2020/5/3
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String s = "modify me!!!!!";
        modifyStr(s);
        System.out.println(s);
        Integer i = 1;
        i++;

    }
    static void modifyStr(String s) throws NoSuchFieldException, IllegalAccessException {
        // 1.获取String类中的value字段
        Field field = String.class.getDeclaredField("value");
        // 2.在用反射时访问私有变量，需设置为true
        field.setAccessible(true);
        // 3.接收该String对象存的value值
        char value[]= (char[]) field.get(s);
        // 4.通过数组索引赋值修改
        // 注意：数组对象的大小是固定的，这儿不能扩容
        String modify = "modify success";
        for (int i = 0; i < value.length; i++) {
            value[i] = modify.charAt(i);
        }
    }
    static void modifyStr1(String s){
        s = "try to modify";
    }
}
