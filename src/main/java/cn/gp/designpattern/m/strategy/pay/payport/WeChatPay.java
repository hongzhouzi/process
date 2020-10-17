package cn.gp.designpattern.m.strategy.pay.payport;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class WeChatPay extends Payment {
    @Override
    public String getName() {
        return "微信支付";
    }
    @Override
    protected double queryBalance(String uid) {
        return 300;
    }
}

