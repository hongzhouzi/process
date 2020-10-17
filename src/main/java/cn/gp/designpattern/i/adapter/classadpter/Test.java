package cn.gp.designpattern.i.adapter.classadpter;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {

        Target adapter = new cn.gp.designpattern.i.adapter.classadpter.Adapter();
        System.out.println(adapter.request());
    }
}
