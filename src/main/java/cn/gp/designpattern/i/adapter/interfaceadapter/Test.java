package cn.gp.designpattern.i.adapter.interfaceadapter;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        Adapter adapter = new Adapter(new Adaptee()) {
            @Override
            public int request() {
                return adaptee.specificRequest() / 10;
            }

            /*
            根据需要选择性重写一些方法自由适配
             */
            @Override
            public int request1() {
                return adaptee.specificRequest() / 5;
            }

        };
        int result = adapter.request();
        int result1 = adapter.request1();
        System.out.println(result+"====="+result1);
    }
}
