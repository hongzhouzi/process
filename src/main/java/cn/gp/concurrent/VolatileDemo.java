package cn.gp.concurrent;

/**
 * @author hongzhou.wei
 * @date 2020/8/31
 */
public class VolatileDemo {

     private static volatile boolean stop = false;

    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
//                new File("/a");
//                System.out.println("rs:" + i);
//                synchronized (VolatileDemo.class){ }
                /*try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
    }
}
