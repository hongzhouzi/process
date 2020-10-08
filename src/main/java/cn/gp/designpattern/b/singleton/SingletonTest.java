package cn.gp.designpattern.b.singleton;

import cn.gp.designpattern.b.singleton.lazy.LazyDoubleCheckSingleton;
import cn.gp.designpattern.b.singleton.lazy.LazyInnerClassSingleton;
import cn.gp.designpattern.b.singleton.lazy.LazySingleton;
import cn.gp.designpattern.b.singleton.registry.ContainerSingleton;
import cn.gp.designpattern.b.singleton.registry.EnumSingleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * @author hongzhou.wei
 * @date 2020/10/2
 */
public class SingletonTest {
    public static void main(String[] args) {
        SingletonTest test = new SingletonTest();
//        test.concurrentTest();
//        test.reflectTest();
        test.f();
    }

    /**
     * 反射破坏测试
     */
    void reflectTest(){
        try {
            // 在比较无聊的情况下，故意破坏
            // 0、拿到类信息
//            Class<?> clazz = LazyInnerClassSingleton.class;
            Class<?> clazz = EnumSingleton.class;
            // 1、通过反射获取构造方法
//            Constructor<?> c = clazz.getDeclaredConstructor(null);
            // 因为枚举类的构造方法有这两个参数
            Constructor<?> c = clazz.getDeclaredConstructor(String.class, int.class);
            ///2、强制访问，可以拿到加private修饰的东西
            c.setAccessible(true);
            // 3、暴力初始化，调用多次构造方法
            Object o1 = c.newInstance();
            Object o2 = c.newInstance();
            System.out.println(o1 == o2);
            System.out.println(o1 + "\n" + o2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 序列化破坏测试
     */
    void serializableTest(){

        SerializableSingleton s1 = null;
        SerializableSingleton s2 = SerializableSingleton.getInstance();

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream("SerializableSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("SerializableSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (SerializableSingleton)ois.readObject();
            ois.close();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通测试
     */
    void f(){
        // ==============EnumSingleton======================
        EnumSingleton s = EnumSingleton.getInstance();
        s.setData(new Object());
        Object data = EnumSingleton.getInstance().getData();
        EnumSingleton s1 = EnumSingleton.getInstance();
        s1.setData(new Object());
        Object data1 = s1.getData();
        System.out.println(data ==data1);

        // ==============LazyDoubleCheckSingleton======================
        LazyDoubleCheckSingleton doubleCheckSingleton1 = LazyDoubleCheckSingleton.getInstance();
        LazyDoubleCheckSingleton doubleCheckSingleton2 = LazyDoubleCheckSingleton.getInstance();
        System.out.println(doubleCheckSingleton1 == doubleCheckSingleton2);

        // ==============LazyInnerClassSingleton======================
        LazyInnerClassSingleton innerClassSingleton1 = LazyInnerClassSingleton.getInstance();
        LazyInnerClassSingleton innerClassSingleton2 = LazyInnerClassSingleton.getInstance();
        System.out.println(innerClassSingleton1 == innerClassSingleton2);

        // ==============ContainerSingleton======================
        Object containerSingleton1 = ContainerSingleton.getBean("cn.gp.designpattern.b.singleton.SingletonTest");
        Object containerSingleton2 = ContainerSingleton.getBean("cn.gp.designpattern.b.singleton.SingletonTest");
        System.out.println(containerSingleton1 == containerSingleton2);

        // ==============ContainerSingleton======================
        ThreadLocalSingleton threadLocalSingleton1 = ThreadLocalSingleton.getInstance();
        ThreadLocalSingleton threadLocalSingleton2 = ThreadLocalSingleton.getInstance();
        System.out.println(threadLocalSingleton1 == threadLocalSingleton2);
    }
    /**
     * 多线程环境测试
     */
    void concurrentTest(){
        new Thread( new SingletonRunnableTest()).start();
        new Thread( new SingletonRunnableTest()).start();
        System.out.println("end");
    }

    static class SingletonRunnableTest implements Runnable{

        @Override
        public void run() {
//            LazySingleton lazySingleton = LazySingleton.getInstance();
//            LazyDoubleCheckSingleton lazySingleton = LazyDoubleCheckSingleton.getInstance();
            LazyInnerClassSingleton lazySingleton = LazyInnerClassSingleton.getInstance();
            System.out.println(Thread.currentThread().getName()+" : "+lazySingleton);
        }
    }
}
