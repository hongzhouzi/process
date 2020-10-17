package cn.gp.designpattern.j.bridge.message;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        // 发送信息方式 + 紧急程度 组合
        IMessage message = new SmsMessage();
        AbastractMessage abastractMessage = new NomalMessage(message);
        abastractMessage.sendMessage("XX申请","魏总");

        message = new EmailMessage();
        abastractMessage = new UrgencyMessage(message);
        abastractMessage.sendMessage("XX申请","魏总");
    }
}
