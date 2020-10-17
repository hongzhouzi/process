package cn.gp.designpattern.n.chain;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class ConcreteHandlerB extends Handler {

    @Override
    public void handleRequest(String request) {
        if ("requestB".equals(request)) {
            System.out.println(this.getClass().getSimpleName() + "deal with request: " + request);
            return;
        }
        if (this.nextHandler != null) {
            this.nextHandler.handleRequest(request);
        }
    }
}
