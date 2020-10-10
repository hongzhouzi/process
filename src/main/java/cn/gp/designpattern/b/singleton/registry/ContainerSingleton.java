package cn.gp.designpattern.b.singleton.registry;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongzhou.wei
 * @date 2020/10/7
 */
public class ContainerSingleton {
    private ContainerSingleton(){}
    private static Map<String, Object> ioc = new ConcurrentHashMap<String, Object>();

    public static Object getBean(String className){
        synchronized (ioc){
            if(!ioc.containsKey(className)){
                Object obj = null;
                try {
                    obj = Class.forName(className).newInstance();
                    ioc.put(className, obj);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return obj;
            }else {
                return ioc.get(className);
            }
        }
    }

    // 方法一：双重检查锁
    public static Object getInstance(String className) {
        Object instance = null;
        if (!ioc.containsKey(className)) {
            synchronized (ContainerSingleton.class) {
                if (!ioc.containsKey(className)) {
                    try {
                        instance = Class.forName(className).newInstance();
                        ioc.put(className, instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return instance;
                } else {
                    return ioc.get(className);
                }
            }
        }
        return ioc.get(className);
    }

    // 方法二：利用ConcurrentHashMap#putIfAbsent()方法的原子性
    public static Object getInstance1(String className){
        try {
            ioc.putIfAbsent(className, Class.forName(className).newInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ioc.get(className);
    }
}
