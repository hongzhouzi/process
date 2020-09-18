package cn.gp.concurrent.async.chain.of.resp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 保存处理
 *
 * @author hongzhou.wei
 * @date 2020/9/14
 */
public class SaveProcessor extends Thread implements RequestProcessor {
    RequestProcessor nextProcessor;
    BlockingQueue<Request> requests = new LinkedBlockingDeque();
    volatile  boolean finished = false;

    public SaveProcessor(RequestProcessor processor){
        this.nextProcessor = processor;
    }

    @Override
    public void run() {
        while(!finished){
            try {
                Request request=requests.take();
                System.out.println("Save:"+request);
                nextProcessor.processReq(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        System.out.println("SaveProcessor begin shutdown");
        finished=true;
        requests.clear();
        nextProcessor.shutdown();
    }
}
