package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public abstract class AbastractMessage {
    private IMessage message;

    public AbastractMessage(IMessage message) {
        this.message = message;
    }
    void sendMessage(String message,String toUser){
        this.message.send(message,toUser);
    }
}

