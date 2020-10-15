package cn.gp.designpattern.e.facade;

/**
 * 外观角色类
 * 又叫外观模式，提供了一个统一的接口用来访问子系统中的一群接口。
 * 主要特征是定义了一个高级接口让子系统更容易使用，属于结构性模式。
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class Facade {
    private SubSystemA a =  new SubSystemA();
    private SubSystemB b =  new SubSystemB();
    private SubSystemC c =  new SubSystemC();

    /**
     * 对外接口
     */
    public void doA(){
        a.doA();
    }
    public void doB(){
        b.doB();
    }
    public void doC(){
        c.doC();
    }
}
