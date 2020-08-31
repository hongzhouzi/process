package cn.gp.concurrent;

import java.io.File;

/**
 * @author hongzhou.wei
 * @date 2020/8/31
 */
public class VolatileDemo {

    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
                new File("/a");
//                System.out.println("rs:" + i);
//                synchronized (VolatileDemo.class){ }
                /*try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }).start();
        Thread.sleep(1000);
        stop = true;
    }
}
