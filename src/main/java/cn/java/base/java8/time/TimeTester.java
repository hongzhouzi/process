package cn.java.base.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hongzhou.wei
 * @date 2020/11/19
 */
public class TimeTester {

    @Test
    public void localDate() {
        // 获取XX值
        LocalDate date = LocalDate.of(2020, 11, 20);
        println("获取年份", date.getYear());
        println("获取月份（枚举值）", date.getMonth());
        println("获取月份（数字）", date.getMonthValue());
        println("获取月份（数字）", date.get(ChronoField.MONTH_OF_YEAR));
        println("获取日期（年中第几天）", date.getDayOfYear());
        println("获取日期（年中第几天）", date.get(ChronoField.DAY_OF_YEAR));
        println("获取日期（月中第几天）", date.getDayOfMonth());
        println("获取日期（月中第几天）", date.get(ChronoField.DAY_OF_MONTH));
        println("获取日期（周中第几天）", date.getDayOfWeek().getValue());
        println("获取日期（周中第几天）", date.get(ChronoField.DAY_OF_WEEK));
        println("是否闰年", date.isLeapYear());

        // 加减时间
        println("加2月", date.plusMonths(2));
        println("加2月", date.plus(2, ChronoUnit.MONTHS));
        println("减2月", date.minusMonths(2));
        println("减2月", date.minus(2, ChronoUnit.MONTHS));


        // 字符串转日期
        LocalDate parse = LocalDate.parse("2020-09-09");
        println("获取日期（周中第几天）", parse.getDayOfWeek().getValue());

        // 获取当前时间
        LocalDate now = LocalDate.now();
        LocalDate now1 = LocalDate.now(Clock.systemUTC());

        // 时间间隔
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 20, 11, 11, 11);
        LocalDateTime nowDateTime = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, nowDateTime);
        println("间隔秒", duration.getSeconds());
        println("间隔纳秒", duration.getNano());

        Period period = Period.between(date, now);
        println("间隔天", period.getDays());
        println("间隔月", period.getMonths());
        println("间隔年", period.getYears());


    }

    @Test
    public void localDateTime() {
        String s = "";
        List<Long> collect = Arrays.stream(s.split(","))
            .filter(it -> !"".equals(it))
            .map(Long::new)
            .collect(Collectors.toList());
        System.out.println(collect);


        AtomicInteger index = new AtomicInteger();
        int[] i = {0};
        IntStream.range(0, 10)
            .forEach(it -> {
                index.addAndGet(1);
                System.out.println(i[0]++);
            });


    }

    @Test
    public void dateTimeFormatter() {
        LocalDate date1 = LocalDate.of(2014, 3, 18);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date1.format(formatter);
        LocalDate parse = LocalDate.parse(formattedDate, formatter);

    }

    @Test
    public void la() {
        /*Comparator.comparing()
            .reversed()
            .thenComparing()*/
        // 函数复合
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        // 数学上写作 g(f(x))
        Function<Integer, Integer> h1 = f.andThen(g);
        // 数学上写作 f(g(x))
        Function<Integer, Integer> h2 = f.compose(g);
        int res = h1.apply(1);

        LocalDateTime of = LocalDateTime.of(2020, 11, 2, 0, 0, 0);
        int compareTo = LocalDateTime.now().compareTo(of);
        if(compareTo >= 0){
            System.out.println("ssss");
        }
        System.out.println(compareTo);
    }


    private void println(String title, Object o) {
        System.out.println(title + "：" + o);
    }
}
