package cn.gp.concurrent.producer_consumer;

import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型-消费者
 *
 * @author hongzhou.wei
 * @date 2020/9/10
 */
public class Consumer implements Runnable {

    /**
     * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过来；
     * 或者定义成类变量，生产者消费者通过类取到该变量。
     */
    Deque<String> msg;
    int maxSize ;
    Lock lock;
    Condition condition;

    public Consumer(Deque queue, int maxSize, Lock lock, Condition condition){
        this.msg = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }


    @Override
    public void run() {
        while (true){
            // 0.抢占锁，准备消费（注意位置是在while(true)里面）
            lock.lock();
            try{
                // 1.队列空了，阻塞等待
                while (msg.isEmpty()){
                    System.out.println("队列【空】了，等待生产中……");
                    // 阻塞线程并释放锁
                    condition.await();
                }
                Thread.sleep(1000);
                // 2.正常消费消息
                System.out.println("【消费】："+ msg.remove());
                // 3.唤醒生产者
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
