package cn.interview.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有A、B、C 三个线程，A线程 输出“A”，B线程 输出“B”，C线程 输出“C”，要求同时启动3个线程，
 * 按照顺序输出“ABC”，循环10次，请使用代码实现。
 * <p>
 * 【分析】：控制线程顺序可行方案
 * 1.共享标识：循环总数的余数来指定当前进来的线程执行哪段代码。
 * 2.互斥锁：1) synchronized和Obejct的wait()、notify; 2) ReentrantLock和Condition类await 、signal
 * 3.原子类型变量：
 * <p>
 * <p>
 * 顺序问题：计数器、前驱后继节点
 * 多线程环境数据的问题：锁、原子类型变量
 *
 * @author hongzhou.wei
 * @date 2020/9/17
 */
public class Thread3OutAbc {
    public static void main(String[] args) {
       solution2();
//        new Thread3OutAbc().solution3();
//        new Thread3OutAbc().solution4();
    }

    static void solution1() {
        new Demo1().start();
        new Demo1().start();
        new Demo1().start();
    }

    static void solution2() {
        new MyThread(0, "A").start();
        new MyThread(1, "B").start();
        new MyThread(2, "C").start();
    }

    private static       AtomicInteger atomicCounter = new AtomicInteger(0);
    private static final int           MAX           = 30;

    void solution3() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(new MyRunnable(0, "A"));
        service.execute(new MyRunnable(1, "B"));
        service.execute(new MyRunnable(2, "C"));
        service.shutdown();
    }

    void solution4() {
        // 设置线程间的链式关系
        MyLinkedThread c = new MyLinkedThread("C");
        MyLinkedThread b = new MyLinkedThread(c, "B");
        MyLinkedThread a = new MyLinkedThread(b, "A");
        // 初始化线程的头节点和下个节点（从a线程开始）
        headThread = nextThread = a;
        a.start();
        b.start();
        c.start();
    }

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
            while (atomicCounter.get() < MAX) {
                System.out.println("========" + Thread.currentThread().getName() + "==========");
                System.out.println("=====" + Thread.currentThread().getName() + "===atomicCounter:" + atomicCounter.get() + "====flag:" + flag + "==========");
                // 任由CPU怎么调度，反正只在输出结果这儿把握好就OK。用原子类变量就可以不用加锁操作，用普通变量就需要加锁
                if (atomicCounter.get() % 3 == flag) {
                    System.out.println("===" + Thread.currentThread().getName() + "=== will output");
                    System.out.println(value);
                    atomicCounter.incrementAndGet();
                }
            }
        }
    }

    private static MyLinkedThread headThread;
    private static MyLinkedThread nextThread;

    class MyLinkedThread extends Thread {
        /**
         * 标识线程的后继线程
         */
        private MyLinkedThread next;
        private String         value;

        public MyLinkedThread(MyLinkedThread next, String value) {
            this.next = next;
            this.value = value;
        }

        public MyLinkedThread(String value) {
            this.value = value;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (MyLinkedThread.class) {
                    // 当前顺序不应该当前线程执行，等调度
                    Thread currentThread = Thread.currentThread();
                    while (!currentThread.equals(nextThread)) {
                        try {
                            MyLinkedThread.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 执行到这里，表明满足条件，打印
                    System.out.println("===" + "===" + Thread.currentThread().getName());
                    System.out.println(value);
                    nextThread = next == null ? headThread : next;
                    // 调用notifyAll方法
                    MyLinkedThread.class.notifyAll();
                }
            }
        }
    }
}


class Demo1 extends Thread {
    private static int    count = 1;
    private    static     String lock  = "abc";

    @Override
    public void run() {
        synchronized (lock) {
            while (count < 30) {
                System.out.println("=======" + count + "=======" + Thread.currentThread().getId());
                switch (count % 3) {
                    case 1:
                        System.out.println("A");
                        break;
                    case 2:
                        System.out.println("B");
                        break;
                    case 0:
                        System.out.println("C");
                        break;
                    default:
                }
                count++;
            }
        }
    }
}

class MyThread extends Thread {
    /**
     * 多个线程实例共有（类变量），不用volatile修饰，操作该值时是加锁状态不存在多个线程同时修改
     * 该变量累加，结合线程标识状态控制打印顺序
     */
    private static int       counter;
    /**
     * 标识ABC三个线程，0:A;   1:B;   2:C;
     */
    private        int       flag;
    private        String    value;
    /**
     * 必须声明成类变量，需要加类级别的锁（这儿对象级别的锁在实例化线程时每个线程都会有个，显然锁不住）
     */
    private static Lock      lock      = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public MyThread(int flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // 1.加锁
            /*lock.lock();
            // 2.判断当前线程
            System.out.println("===" + counter + "===" + Thread.currentThread().getName() + "=== lock exclusiveOwnerThread");
            System.out.println("===counter:" + counter + "===counter % 3:" + counter % 3 + "===flag:" + flag + "===currentThread:" + Thread.currentThread().getName() + "===");
            // 注意这儿要用while，把线程批量唤醒后，如果不是该轮到的线程要让其继续阻塞
            while (counter % 3 != flag) {
                try {
                    System.out.println("=====" + Thread.currentThread().getName() + " will await");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 执行到这里，表明满足条件，打印
            System.out.println("===" + counter + "===" + Thread.currentThread().getName() + "=== will output");
            System.out.println(value);
            counter++;
            System.out.println("=====" + Thread.currentThread().getName() + " will signalAll");
            condition.signalAll();
            System.out.println("===" + counter + "===" + Thread.currentThread().getName() + "=== will unlock");
            lock.unlock();*/

            synchronized (MyThread.class){
                // 阻塞其他不符合条件的
                while (counter % 3 != flag){
                    try {
                        MyThread.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 输出符合条件的value
                System.out.println(value);
                counter++;

                // 唤醒其他线程
                MyThread.class.notifyAll();
            }
        }
    }
}





/*class MyThread extends Thread {
    // 线程共有，判断所有的打印状态
    private static int counter;
    // 必须声明成类变量
    private static Object obj = new Object();
    // 0：打印A；1：打印B；2：打印C
    private int flag;
    private String value;

    public MyThread(int flag,String value) {
        this.flag = flag;
        this.value=value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (obj) {
                while (counter % 3 != flag) {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 执行到这里，表明满足条件，打印
                System.out.println("===" +counter + "===" + Thread.currentThread().getId());
                System.out.println(value);
                counter++;
                // 调用notifyAll方法
                obj.notifyAll();
            }
        }
    }
}*/

/*class Demo1 extends Thread {
    volatile static int count = 0;
    String value;

    public Demo1(String name) {
        this.value = name;
    }

    @Override
    public void run() {
        synchronized (Demo1.class) {
            while (count < 30) {
                System.out.println(count + "---" + Thread.currentThread());
                switch (count % 3) {
                    case 0:
                        System.out.println(value);
                        break;
                    case 1:
                        System.out.println(value);
                        break;
                    case 2:
                        System.out.println(value);
                        break;
                    default:
                }
                count++;
            }
        }
    }
}*/
