package cn.gp.designpattern.n.chain;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public abstract class Handler {

    protected Handler nextHandler;

    public void setNextHanlder(Handler successor) {
        this.nextHandler = successor;
    }

    public abstract void handleRequest(String request);

}
