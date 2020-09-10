package cn.gp.concurrent.producer_consumer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型-应用测试类
 *
 * @author hongzhou.wei
 * @date 2020/9/10
 */
public class App {
    public static void main(String[] args) {
        /**
         * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过去；
         * 或者定义成类变量，生产者消费者通过类取到该变量。
         */
        Deque<String> queue = new LinkedList<>();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int maxSize = 5;

        Producer producer = new Producer(queue, maxSize,lock,condition);
        Consumer consumer = new Consumer(queue, maxSize,lock,condition);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
