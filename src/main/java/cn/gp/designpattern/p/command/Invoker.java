package cn.gp.designpattern.p.command;

/**
 * 请求者
 *
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Invoker {
    private ICommand mCmd;

    public Invoker(ICommand cmd) {
        this.mCmd = cmd;
    }

    public void action() {
        this.mCmd.execute();
    }
}
