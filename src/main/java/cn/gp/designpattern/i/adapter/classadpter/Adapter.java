package cn.gp.designpattern.i.adapter.classadpter;

/**
 * 适配器-将源角色(Adaptee)转换为目标角色(Target)
 *
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Adapter extends Adaptee implements Target {
    /**
     * 实现目标角色的接口，在里面调用源角色中的方法获取源数据，
     * 并将源数据处理成目标角色需要的数据
     */
    @Override
    public int request() {
        return super.specificRequest()/10;
    }
}
