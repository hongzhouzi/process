package cn.gp.designpattern.t.observer.guavaevent;

import com.google.common.eventbus.EventBus;

/**
 * @author hongzhou.wei
 * @date 2020/10/19
 */
public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new GuavaEvent());
        eventBus.register(new GuavaEvent());
        eventBus.register(new GuavaEvent());

        eventBus.post("whz");
    }
}
