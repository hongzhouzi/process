package cn.java.base.interfac;

/**
 * @author hongzhou.wei
 * @date 2020/5/5
 */
public class AndroidPhone implements ICall, IGame {
    @Override
    public void call() {
        System.out.println("Android phone call");
    }

    // 继承的多个接口中有相同的方法
    @Override
    public void basic(){
        System.out.println("Android phone basic function");
    }


    @Override
    public void game() {
        System.out.println("Android phone game");
    }
}
