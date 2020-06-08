package cn.java.base.java8;

import cn.java.base.java8.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamPractice {
    public static void main(String[] args) {

    }

    static void createStream() {
        //1.集合提供的stream方法
        List<String> list = new ArrayList<>();
        Stream stream = list.stream();
        Stream stream1 = list.parallelStream();

        //2.通过 Arrays 中的静态方法stream()获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        //3.通过Stream 类中的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4.创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);

    }

    public void ff() {
        List<Integer> v = Arrays.asList(2, 3, 5, 7, 9, 10);
        int re = v.stream()
                .filter(e -> e > 3)
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .findFirst()
                .orElse(0);
        System.out.println(re);
    }

    static List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 58, 5555.55),
            new Employee("王五", 26, 3333.33),
            new Employee("赵六", 36, 6666.66),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88)
    );

    /**
     * 筛选与切片-中间操作
     * filter--接收Lambda，从流中排除某些元素。
     * limit--截断流，使其元素不超过给定数量。
     * skip(n)--跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n) 互补
     * distinct--筛选，通过流所生成元素的 hashCode() 和 equals() 去掉重复元素
     */
    @Test
    public void t() {
        Stream stream = employees.stream()
                //中间操作：不会执行任何操作
                // filter：内部迭代；必须有返回值(boolean)，若只有一条语句可直接写该语句不用写return
                .filter(e -> e.getAge() < 35)
                // limit：只要找到n个符合条件的就不再继续迭代
                .limit(5)

                .skip(2)
                // distinct：去重，重写hashCode() 和 equals()
                .distinct();
        //终止操作：一次性执行全部内容，即 惰性求值
        stream.forEach(System.out::println);
    }

    /**
     * 映射-中间操作
     * map--接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素。
     * flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void testMap() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println("------------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        System.out.println("------------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(StreamPractice::filterCharacter);
        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });
        System.out.println("------------------------");

        // flatMap
        Stream<Character> sm = list.stream()
                .flatMap(StreamPractice::filterCharacter);
        sm.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     */
    @Test
    public void testSort() {
        // 自然排序
        List<Integer> list = Arrays.asList(1, 5, 8, 3, 2);
        list.stream()
                .sorted()
                .forEach(System.out::println);

        // 按照对象中的某个属性排序
        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .forEach(System.out::println);

        // 对象中多个属性排序，年龄排序、年龄相同工资排序
        employees.stream()
                //返回int值，比较时用compareTo
                .sorted((e1, e2) -> {
                    if (e1.getAge().equals(e2.getAge())) {
                        return e1.getSalary().compareTo(e2.getSalary());
                    } else {
                        return e1.getAge().compareTo(e2.getAge());
                    }
                })
                .forEach(System.out::println);
    }

    /**
     * 自己生成流：
     * 用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的 Stream。
     */
    @Test
    public void testGenerate() {
        Random seed = new Random();
        Supplier<Integer> supplier = seed::nextInt;
        Stream.generate(supplier)
                .limit(12)
                .sorted()
                .forEach(System.out::println);
        // 指定范围内的随机数
        System.out.println(seed.ints(50, 60).findFirst().getAsInt());
    }
}
