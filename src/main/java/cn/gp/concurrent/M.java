package cn.gp.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hongzhou.wei
 * @date 2021/2/2
 */
public class M {
    @Test
    public void join() throws InterruptedException {
        final Thread a = new Thread(() -> System.out.println("A"));
        final Thread b = new Thread(() -> {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        });
        final Thread c = new Thread(() -> {
            try {
                b.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
        });

        a.start();
        b.start();
        c.start();
        a.join();
        System.out.println("end");
    }

    @Test
    public void auto(){
        AtomicInteger inc = new AtomicInteger();
        final int MAX = 30;

        class MyRunnable implements Runnable {
            /**
             * 标识ABC三个线程，0:A;   1:B;   2:C;
             */
            private int    flag;
            private String value;

            public MyRunnable(int flag, String value) {
                this.flag = flag;
                this.value = value;
            }

            @Override
            public void run() {
                while (inc.get() < MAX) {
                    // System.out.println("===" + Thread.currentThread().getName() + "===");
                    // 任由CPU怎么调度，反正只在输出结果这儿把握好就OK。用原子类变量就可以不用加锁操作
                    if (inc.get() % 3 == flag) {
                        System.out.println(value);
                        inc.incrementAndGet();
                    }
                }
            }
        }

    }

}
