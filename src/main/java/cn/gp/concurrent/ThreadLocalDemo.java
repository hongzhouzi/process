package cn.gp.concurrent;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hongzhou.wei
 * @date 2020/9/4
 */
public class ThreadLocalDemo {

    private static volatile Object[] objects = new Object[10]; // 查看字节码文件，有lock指令
    public static void main(String[] args) {
        /*objects[0] = 1; // 查看字节码文件，无lock指令
        objects = new Object[5]; // 查看字节码文件，有lock指令
        ThreadLocal<Integer> local = new ThreadLocal<Integer>(){

        };
        Lock lock = new ReentrantLock();
        lock.lock();
        while (Thread.currentThread().isInterrupted()){
            // todo
            Thread.currentThread().interrupt();
        }
        lock.unlock();*/

        ThreadLocalDemo cur = new ThreadLocalDemo();
        cur.doAfter(()-> System.out.println("hello"));

    }

    ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(10);
    private void doAfter(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }

}
