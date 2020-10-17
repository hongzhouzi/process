package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public interface IMessage {
    //发送消息的内容和接收人
    void send(String message,String toUser);
}
