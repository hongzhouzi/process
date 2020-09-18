package cn.gp.concurrent.async.chain.of.resp;

/**
 * 异步化责任链-请求处理服务
 *
 * @author hongzhou.wei
 * @date 2020/9/14
 */
public class ChainServer {
    private RequestProcessor firstProcessor;

    private void setupRequestProcessor(){
        // 构建一个请求处理链路：先保存再打印
        PrintProcessor printProcessor=new PrintProcessor();
        firstProcessor=new SaveProcessor(printProcessor);
        // 分别启动两个线程
        printProcessor.start();
        ((SaveProcessor)firstProcessor).start();
    }
    public void startup(){
        setupRequestProcessor();
    }
    public void shutdown(){
        firstProcessor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        ChainServer chainServer=new ChainServer();
        chainServer.startup();

        Request request=new Request("Mic");
        chainServer.firstProcessor.processReq(request);

        Thread.sleep(5000);

        chainServer.shutdown();
    }
}
