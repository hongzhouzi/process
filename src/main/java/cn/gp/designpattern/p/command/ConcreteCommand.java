package cn.gp.designpattern.p.command;

/**
 * 具体命令
 *
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class ConcreteCommand implements ICommand {
    // 直接创建接收者，不暴露给客户端
    private Receiver mReceiver = new Receiver();

    @Override
    public void execute() {
        this.mReceiver.action();
    }
}
