package cn.gp.designpattern.m.strategy.pay.payport;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class AliPay extends Payment {
    @Override
    public String getName() {
        return "支付宝";
    }
    @Override
    protected double queryBalance(String uid) {
        return 900;
    }
}

