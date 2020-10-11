package cn.gp.designpattern.d.proxy.dbroute;

/**
 * @author hongzhou.wei
 * @date 2020/10/11
 */
public class OrderServiceImpl implements IOrderService{
    @Override
    public int createOrder(Order order) {
        System.out.println("OrderService调用orderDao创建订单");
        return 1;
    }
}
