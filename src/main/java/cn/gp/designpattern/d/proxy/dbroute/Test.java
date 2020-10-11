package cn.gp.designpattern.d.proxy.dbroute;


import cn.gp.designpattern.d.proxy.dbroute.proxy.OrderServiceDynamicProxy;
import cn.gp.designpattern.d.proxy.dbroute.proxy.OrderServiceStaticProxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hongzhou.wei
 * @date 2020/10/11
 */
public class Test {
    public static void main(String[] args) {
        try {
            Order order = new Order();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2019/03/01");
            order.setCreateTime(date.getTime());

            IOrderService orderService = (IOrderService)new OrderServiceDynamicProxy().getInstance(new OrderServiceImpl());
//            IOrderService orderService = (IOrderService)new OrderServiceStaticProxy(new OrderServiceImpl());
            orderService.createOrder(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
