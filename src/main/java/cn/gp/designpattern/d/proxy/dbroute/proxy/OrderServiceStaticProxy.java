package cn.gp.designpattern.d.proxy.dbroute.proxy;

import cn.gp.designpattern.d.proxy.dbroute.IOrderService;
import cn.gp.designpattern.d.proxy.dbroute.Order;
import cn.gp.designpattern.d.proxy.dbroute.db.DynamicDataSourceEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 静态代理类
 *
 * @author hongzhou.wei
 * @date 2020/10/11
 */
public class OrderServiceStaticProxy implements IOrderService {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private IOrderService orderService;
    public OrderServiceStaticProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        // ========== 前置操作，根据年份切换数据源==========
        Long time = order.getCreateTime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_" +  dbRouter + "】数据源处理数据" );
        DynamicDataSourceEntity.set(dbRouter);

        // ========== 调用真实主题类的方法==========
        this.orderService.createOrder(order);

        // ========== 后置操作，切换回默认数据源==========
        DynamicDataSourceEntity.restore();
        return 0;
    }
}
