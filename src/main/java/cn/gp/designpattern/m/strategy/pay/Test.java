package cn.gp.designpattern.m.strategy.pay;

import cn.gp.designpattern.m.strategy.pay.payport.PayStrategy;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        Order order = new Order("1","2020031401000323",824.5);
        System.out.println(order.pay(PayStrategy.ALI_PAY));
        System.out.println(order.pay(PayStrategy.WECHAT_PAY));
    }
}
