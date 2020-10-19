package cn.gp.designpattern.t.observer.guavaevent;

import com.google.common.eventbus.Subscribe;

/**
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public class GuavaEvent {
    @Subscribe
    public void subscribe(String str) {
        System.out.println("执行subscribe()，入参是：" + str);
    }
}
