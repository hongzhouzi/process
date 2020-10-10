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
/*
// ===============用jad反编译结果===============
public final class EnumSingleton extends Enum
{

    public static EnumSingleton[] values()
    {
        return (EnumSingleton[])$VALUES.clone();
    }

    public static EnumSingleton valueOf(String name)
    {
        return (EnumSingleton)Enum.valueOf(cn/gp/designpattern/b/singleton/registry/EnumSingleton, name);
    }

    private EnumSingleton(String s, int i)
    {
        super(s, i);
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public static EnumSingleton getInstance()
    {
        return INSTANCE;
    }

    public static final EnumSingleton INSTANCE;
    private Object data;
    private static final EnumSingleton $VALUES[];

    static
    {
        INSTANCE = new EnumSingleton("INSTANCE", 0);
        $VALUES = (new EnumSingleton[] {
            INSTANCE
        });
    }
}
 */