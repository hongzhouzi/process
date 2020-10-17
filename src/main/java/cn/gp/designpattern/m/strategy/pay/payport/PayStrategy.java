package cn.gp.designpattern.m.strategy.pay.payport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class PayStrategy {
    public static  final String ALI_PAY = "AliPay";
    public static  final String WECHAT_PAY = "WechatPay";
    public static  final String DEFAULT_PAY = ALI_PAY;

    private static Map<String,Payment> strategy = new HashMap<String,Payment>();

    static {
        strategy.put(ALI_PAY,new AliPay());
        strategy.put(WECHAT_PAY,new WeChatPay());
    }

    public static Payment get(String payKey){
        if(!strategy.containsKey(payKey)){
            return strategy.get(DEFAULT_PAY);
        }
        return strategy.get(payKey);
    }
}
