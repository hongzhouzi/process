package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class UrgencyMessage extends AbastractMessage {
    public UrgencyMessage(IMessage message) {
        super(message);
    }

    @Override
    void sendMessage(String message, String toUser){
        message = "【加急】" + message;
        super.sendMessage(message,toUser);
    }

    public Object watch(String messageId){
        return null;
    }
}
