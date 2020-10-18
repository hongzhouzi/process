package cn.gp.designpattern.p.command;

/**
 * 接收者
 *
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Receiver {
    public void action() {
        System.out.println("根据某命令执行的具体操作");
    }
}
