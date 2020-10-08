package cn.gp.designpattern.b.singleton;

import cn.gp.designpattern.b.singleton.hungry.HungrySingleton;

import java.io.Serializable;

/**
 * @author hongzhou.wei
 * @date 2020/10/7
 */
public class SerializableSingleton implements Serializable {

    private static SerializableSingleton instance = new SerializableSingleton();

    private SerializableSingleton() {
    }

    public static SerializableSingleton getInstance() {
        return instance;
    }

    /**
     * 防止序列表破坏单例，但反序列化时会被实例化多次，只是新创建的对象没有被返回
     * 如果创建对象的频率加快，就意外着内存分配开销也会加大
     */
    private Object readResolve(){
        return instance;
    }
}
