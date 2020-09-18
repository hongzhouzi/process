package cn.gp.concurrent.async.chain.of.resp;

/**
 * 异步化责任链-请求处理接口
 *
 * @author hongzhou.wei
 * @date 2020/9/14
 */
public interface RequestProcessor {
    void processReq(Request request);

    void shutdown();
}
