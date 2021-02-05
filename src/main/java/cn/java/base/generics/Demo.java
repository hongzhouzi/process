package cn.java.base.generics;

import cn.gp.distributed.serializer.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author hongzhou.wei
 * @date 2021/1/11
 */
public class Demo {

    @Test
    public void f() {
        // 创建不同类型数组： Integer, Double 和 Character
        Integer[] intArray = {1, 2, 3};
        String[] stringArray = {"Hello", "World"};
        printArray(intArray);
        printArray(stringArray);
        System.out.println(intArray + "-----" + intArray.hashCode());
    }

    public static <E> void printArray(E[] inputArray) {
        for (E element : inputArray) {
            System.out.printf("%s ", element);
        }
        System.out.println();
    }

    @Test
    public void s() {


        Integer num1 = 128000;
        Integer num2 = 169000;
        Integer num3 = 169000;
        System.out.println(num2.hashCode());
        System.out.println(num3.hashCode());

        System.out.println("num1 = " + num1 + ";num2 = " + num2);

//        swap(num1, num2);
        try {
            swapAddr(num1, num2);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("num1 = " + num1 + ";num2 = " + num2);
    }

    public static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    public static void swapAddr(Integer a, Integer b) throws Exception {
        Field a1 = a.getClass().getDeclaredField("value");
        Field b1 = b.getClass().getDeclaredField("value");
        a1.setAccessible(true);
        b1.setAccessible(true);
        // 这个临时变量需要用包装类new的方式，引用赋值的方式改不了
        // 如果用int接收，对于[-128, 127]的数字会用缓存
//        int temp = a;
        Integer temp = new Integer(a);
        a1.set(a, b);
        b1.set(b, temp);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    @Test
    public void ff() {
        Map<String, String> map = new HashMap<>();
        System.out.println(map.put("1", "4"));
        System.out.println(map.get("1"));

        System.out.println(map.put("1", "5"));
        System.out.println(map.get("1"));

        System.out.println(map.putIfAbsent("1", "6"));
        System.out.println(map.get("1"));

        System.out.println(map.putIfAbsent("2", "1"));
        System.out.println(map.get("2"));

        /*Integer a[] = {1, 2, 3};
        // 适配器模式的体现，底层依然是数组，若把数组中的元素改了这儿list会跟着变
        List<Integer> myList = Arrays.asList(a);

        // 推荐写法
        List<Integer> myList1 = new ArrayList<>(Arrays.asList(a));
        List<Integer> myList2 = Arrays.stream(a).collect(Collectors.toList());
        myList.forEach(it -> System.out.print(it + " "));
        a[0]=55;
        myList.forEach(it -> System.out.print(it + " "));
        myList.add(4);//运行时报错：UnsupportedOperationException
        myList.remove(1);//运行时报错：UnsupportedOperationException
        myList.clear();
*/

        Map map1 = new HashMap();
        map1.put(null,"x");
        map1.put(null,"y");
        System.out.println(map1.size());

        TreeMap<User,String> treeMap = new TreeMap<>((o1, o2) -> -o1.getAge() + o2.getAge());
    }

    @Test
    public void iter(){
        List<String> list = new ArrayList<>(Arrays.asList("张三","李四"));
        for (String o : list) {
            System.out.println(o);
        }

        list.forEach(System.out::println);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }
}
