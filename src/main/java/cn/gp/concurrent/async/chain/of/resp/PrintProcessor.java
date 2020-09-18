package cn.gp.concurrent.async.chain.of.resp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 打印处理
 *
 * @author hongzhou.wei
 * @date 2020/9/14
 */
public class PrintProcessor extends Thread implements RequestProcessor {
    private          BlockingQueue<Request> requests = new LinkedBlockingDeque<>();
    private volatile boolean                finished = false;

    @Override
    public void run() {
        while (!finished || !Thread.currentThread().isInterrupted()) {
            try {
                Request request = requests.take();
                System.out.println("Save Processor:" + request);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 处理请求，将来的请求添加到阻塞队列中
     *
     * @param request
     */
    @Override
    public void processReq(Request request) {
        requests.add(request);
    }

    @Override
    public void shutdown() {
        System.out.println("PrintProcessor begin shutdown");
        finished = true;
        requests.clear();
    }
}
