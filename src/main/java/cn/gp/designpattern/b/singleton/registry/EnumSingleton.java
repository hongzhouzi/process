package cn.gp.designpattern.b.singleton.registry;

/**
 * enum是用Enum类实现
 *
 * @author hongzhou.wei
 * @date 2020/10/7
 */
public enum EnumSingleton {
    INSTANCE;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
