package cn.gp.designpattern.k.delegate;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class ConcreteB implements Task {
    @Override
    public void doTask() {
        System.out.println("具体执行者 , B");
    }
}
