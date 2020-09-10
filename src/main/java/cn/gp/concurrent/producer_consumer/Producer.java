package cn.gp.concurrent.producer_consumer;

import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型-生产者
 *
 * @author hongzhou.wei
 * @date 2020/9/10
 */
public class Producer implements Runnable {

    /**
     * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过来；
     * 或者定义成类变量，生产者消费者通过类取到该变量。
     */
    Deque<String> msg;
    int maxSize ;
    Lock lock;
    Condition condition;

    public Producer(Deque queue, int maxSize, Lock lock, Condition condition){
        this.msg = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            i++;
            // 0.抢占锁，准备生产（注意位置是在while(true)里面）
            lock.lock();
            try{
                // 1.达到最大生产数，阻塞并释放锁
                while (msg.size() == maxSize){
                    System.out.println("队列【满】了，等待消费中……");
                    // 释放锁唤醒消费者线程，并将当前线程加入condition队列(阻塞)
                    /*
                    消费者线程被阻塞后释放锁唤醒当前生产者线程，
                    之后消费者线程被加入condition队列(阻塞)
                     */
                    /*
                    还可能是通过consumer中的signal唤醒的，放在同步队列中
                     */
                    condition.await();
                }
                Thread.sleep(1000);
                // 2.生产消息
                msg.add("生产的消息"+i);
                System.out.println("【生产】："+msg.getLast());
                // 3.通知消费者（只要有消息了就去通知，消费者啥时候消费看它啥时候能抢占到锁）
                condition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                // 4.释放锁
                lock.unlock();
            }

        }
    }
}
