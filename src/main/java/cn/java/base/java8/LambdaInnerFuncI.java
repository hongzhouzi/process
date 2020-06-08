package cn.java.base.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * lambda内置核心函数式接口
 */
public class LambdaInnerFuncI {
    public static void main(String[] args) {
        consumer(100, m -> System.out.println("点了份儿鲜椒鱼，消费：￥"+m));
        System.out.println(stringHandler("\t\nhandsome泓舟子   ",s -> s.trim()));

        List<String> list = Arrays.asList("hello","handsome","man","lambda");
        List<String> l = filterStr(list, s -> s.length()>5);
        for (String s : l) {
            System.out.println(s);
        }


    }
    /**
     * Consumer<T> 消费型接口
     * 对类型为T的应用Consumer操作，Consumer.accept(T t)
     */
    static void consumer(double money, Consumer<Double> con){
        con.accept(money);
    }

    /**
     * Supplier<T> 供给型接口
     * 按Supplier生产，Consumer.get()，返回类型为T
     */
    static List<Integer> getNumList(int size, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        // 产生指定个数的整数，并放入集合中
        for(int i=0; i<size; i++){
            list.add(sup.get());
        }
        return list;
    }

    /**
     * Function<T, R> 函数型接口
     * 对类型为T的对象应用Function操作，Function.apply(T t)，返回结果R
     */
    static String stringHandler(String s, Function<String, String> fun){
        return fun.apply(s);
    }

    /**
     *  Predicate<T> 断定型接口
     *  确定类型为T的对象是否满足Predicate接口条件，Predicate.test(T)，返回Boolean
     */
    static List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> strings = new ArrayList<>();
        // 将满足条件的字符串放入list中
        for (String s : list) {
            if(pre.test(s)){
                strings.add(s);
            }
        }
        return strings;
    }
}
