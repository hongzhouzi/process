package cn.java.base.java8;

import cn.java.base.java8.entity.Employee;
import cn.java.base.java8.interfaces.FuncInterface1;
import cn.java.base.java8.interfaces.FuncInterface2;

import java.util.*;

public class Lambda {
    public static void main(String[] args) {
        Comparator<Integer> c = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
        System.out.println(c.compare(4,5));
        Comparator<Integer> d = Integer::compare;

        /**
         1. 调用Collections.sort() 方法，通过定制排序比较两个Employee (先按年龄比，年龄相同按姓名比)，使用Lambda作为参数传递。
         */
        List<Employee> employeeList = Arrays.asList(
                new Employee("李三", 23),
                new Employee("李六",25),
                new Employee("李九",21)
        );
        employeeList.sort((e1, e2) -> {
            if (e1.getAge().equals(e2.getAge())) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }

        /**
         *  2. ①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
               ②声明类TestLambda ，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。。
               ③再将一个字符串的第2个和第4个索引位置进行截取子串。。
         */
        String trim = stringHandler("\t\n泓舟子6B  ", s1 -> s1.trim());
        String upper = stringHandler("abcd", s -> s.toUpperCase());
        String sub = stringHandler("handsome泓舟子",s -> s.substring(8,10));
        System.out.println(trim+"\n"+upper+"\n"+sub);

        /**
         3. ①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值。
            ②接口中声明对应抽象方法。
            ③在TestLambda 类中声明方法，使用接口作为参数，计算两个long 型参数的和。。④再计算两个long 型参数的乘积。。
         */
        op(20L,30L,(x, y) -> x * y);
        op(20L,30L,(x, y) -> x + y);
    }

    /**
     * 处理字符串
     */
    static String stringHandler(String s, FuncInterface1 fun){
        return fun.getValue(s);
    }

    /**
     * 处理两个Long型数据
     */
    static void op(Long l1, Long l2, FuncInterface2<Long, Long> fun){
        System.out.println(fun.getValue(l1, l2));
    }
}
