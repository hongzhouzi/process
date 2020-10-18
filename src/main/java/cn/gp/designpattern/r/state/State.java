package cn.gp.designpattern.r.state;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public abstract class State {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void handle();
}
