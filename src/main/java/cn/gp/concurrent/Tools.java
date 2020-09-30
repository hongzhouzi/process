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
//        concurrentHashMapDemo();
        callableDemo();
    }


    static void concurrentHashMapDemo() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("a", "a");
        System.out.println(concurrentHashMap.get("a"));
        int n = 32;
        int i = Integer.numberOfLeadingZeros(n) | (1 << (15));
        System.out.println("n=" + n + "   numberOfLeadingZeros(n):" + Integer.numberOfLeadingZeros(n) + " | " + (1 << (15)) + " = " + i);
    }

    static void threadPoolDemo(){
        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorService service1 = Executors.newCachedThreadPool();
        ExecutorService service2 = Executors.newScheduledThreadPool(10);
        ExecutorService service3 = Executors.newSingleThreadExecutor();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(15),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy());

        threadPoolExecutor.execute(()-> System.out.println("aaa"));
    }

    static void callableDemo(){
        FutureTask futureTask = new FutureTask(new Callable<String>() {
            @Override
            public String  call() throws Exception {
                return "this is return values";
            }
        });
        new Thread(futureTask).start();

        try {
            Object o = futureTask.get();
            System.out.println(o.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        executorService.execute();
    }

    static void conditionDemo() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            condition.await();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    void sempho() {
        // 传入临牌数
        Semaphore semaphore = new Semaphore(5);
    }

    void countDownLaunch() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
//        countDownLatch.await();
        countDownLatch.countDown();

    }
}
