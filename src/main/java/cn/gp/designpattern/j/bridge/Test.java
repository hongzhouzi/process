package cn.gp.designpattern.j.bridge;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        // 来一个实现化角色
        IImplementor imp = new ConcreteImplementorA();
        // 来一个抽象化角色，聚合实现
        Abstraction abs = new RefinedAbstraction(imp);
        // 执行操作
        abs.operation();
    }
}
