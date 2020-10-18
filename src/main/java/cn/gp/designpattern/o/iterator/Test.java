package cn.gp.designpattern.o.iterator;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Test {
    public static void main(String[] args) {
        //来一个容器对象
        IAggregate<String> aggregate = new ConcreteAggregate<>();
        //添加元素
        aggregate.add("one");
        aggregate.add("two");
        aggregate.add("three");
        //获取容器对象迭代器
        Iterator<String> iterator = aggregate.iterator();
        //遍历
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }
    }
}
