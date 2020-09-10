package cn.gp.concurrent;

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
}
