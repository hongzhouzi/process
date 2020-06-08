package cn.interview.base;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        Integer a = 1, b = 2;
//        swap(a,b);
//        swapRe(a,b);
        swapInteger(a, b);
        System.out.println("a:" + a + " b:" + b);
        float x = 1.0111111f;
        System.out.println(x % 1);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        String string = "a";
        string = "abcdefg";
        modifyStr(string);
        System.out.println(string);
        String s[] = {"aa"};
        final char bb[] = {'1', '2'};
        bb[0] = 'v';
        System.out.println(bb);

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
        String modify = "modify";
        for (int i = 0; i < value.length; i++) {
            value[i] = modify.charAt(i);
        }
    }

    //传参是传入的对象地址，修改对象地址中的值，用set，但Integer没有set，可以用反射来做
    static void swap(Integer a, Integer b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    /*
    形参和实参所占的内存地址不一样，形参中的内容只是实参中存储的对象引用的一份拷贝
交换的是两个引用变量的副本，原来的a,b引用指向不变。
     */
    private static void swapInteger(Integer i1, Integer i2) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            Field field1[] = Integer.class.getDeclaredFields();
            field.setAccessible(true);
            int num = i1;
            field.setInt(i1, i2);
            field.setInt(i2, num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void swapRe(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true); //取消访问修饰符，获得类私有不可变变量
            //public final class Field extends AccessibleObject implements Member
            //值为 true 则指示反射的对象在使用时应该取消 Java语言访问检查。值为 false 则指示反射的对象应该实施 Java语言访问检查。
            //修改常量（final）
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            //交换
            int temp = b;
            field.set(b, a);
            field.set(a, new Integer(temp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
