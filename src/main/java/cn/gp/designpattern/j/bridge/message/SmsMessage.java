package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class SmsMessage implements IMessage {
    @Override
    public void send(String message, String toUser) {
        System.out.println("使用短信消息发送" + message + "给" + toUser);
    }
}
