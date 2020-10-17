package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class EmailMessage implements IMessage {
    @Override
    public void send(String message, String toUser) {
        System.out.println("使用邮件消息发送" + message + "给" + toUser);
    }
}

