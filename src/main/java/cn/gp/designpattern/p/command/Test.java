package cn.gp.designpattern.p.command;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Test {
    public static void main(String[] args) {
        // 创建命令
        ICommand cmd = new ConcreteCommand();
        // 将命令交给请求者执行
        Invoker invoker = new Invoker(cmd);
        invoker.action();
    }
}
