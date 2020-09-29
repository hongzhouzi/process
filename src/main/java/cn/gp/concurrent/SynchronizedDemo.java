package cn.gp.concurrent;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author hongzhou.wei
 * @date 2020/8/26
 */
public class SynchronizedDemo {

    /**
     * 修饰方法
     */
    synchronized void demo() {
        // 临界区
    }

    /**
     * 修饰代码块
     */
    Object obj = new Object();

    void demo1() {
        synchronized (obj) {
            // 临界区
        }
        synchronized (this){

        }
    }


    public static void main(String[] args) {
//        classLayout();
        upgrade();

    }

    /**
     *  默认情况下偏向锁时关闭的，在VM options中加上下面语句开启偏向锁
     *  -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
     */
    /**
     * 打印锁的存储布局
     */
    private static void classLayout() {
        SynchronizedDemo classLayout = new SynchronizedDemo();
        synchronized (classLayout) {
            System.out.println("locking");
            // 当计算了hashcode之后就不是偏向锁了，偏向锁存不了hashcode，升级为重量级锁
//            System.out.println(classLayout.hashCode());
            // 打印对象头，value中展示的二进制和十六进制是按照大端存储模式打印出来的，转成十进制的数据应该从后往前计算每组
            // 大端存储模式：指一个数据的低位字节序的内容放在高地址处，高位字节序存的内容放在低地址处。
            System.out.println(ClassLayout.parseInstance(classLayout).toPrintable());
        }
    }

    /**
     * 锁的升级过程-demo
     */
    private static void upgrade() {
        //
        // 先是t0抢占到锁，此时没有竞争，等待5s后main线程开始抢占锁，锁由偏向锁升级为轻量级锁，再等5s有多个线程抢占锁升级为重量级锁
        SynchronizedDemo classLayout = new SynchronizedDemo();
        new Thread(() -> {
            synchronized (classLayout) {
                System.out.println("t0抢占到锁，暂无竞争");
                System.out.println(ClassLayout.parseInstance(classLayout).toPrintable());
            }
        }).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (classLayout) {
            System.out.println("Main抢占到锁，出现一个竞争");
//            System.out.println(classLayout.hashCode());
            // 打印对象头，value中展示的二进制和十六进制是按照大端存储模式打印出来的，转成十进制的数据应该从后往前计算每组
            // 大端存储模式：指一个数据的低位字节序的内容放在高地址处，高位字节序存的内容放在低地址处。
            System.out.println(ClassLayout.parseInstance(classLayout).toPrintable());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                synchronized (classLayout) {
                    System.out.println(finalI + "抢占到锁，出现多个竞争");
                    System.out.println(ClassLayout.parseInstance(classLayout).toPrintable());
                }
            }).start();
        }
    }
}
