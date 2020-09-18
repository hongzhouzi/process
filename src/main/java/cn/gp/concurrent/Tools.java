package cn.gp.concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hongzhou.wei
 * @date 2020/9/10
 */
public class Tools {
    public static void main(String[] args) {
        concurrentHashMapDemo();
    }



    static void concurrentHashMapDemo(){
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("a","a");
        System.out.println(concurrentHashMap.get("a"));
    }

    static void conditionDemo(){
        Lock lock = new ReentrantLock();
        Condition condition =lock.newCondition();
        lock.lock();
        try {
            condition.await();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    void block() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        // 将信息放入队列中
        blockingQueue.put("whz");
        // 从队列中取数据（阻塞的）
        blockingQueue.take();
        //

    }

    void sempho(){
        // 传入临牌数
        Semaphore semaphore = new Semaphore(5);
    }

    void countDownLaunch(){
        CountDownLatch countDownLatch = new CountDownLatch(3);
//        countDownLatch.await();
        countDownLatch.countDown();

    }
}
