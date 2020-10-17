package cn.gp.designpattern.l.template;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public abstract class AbstractClass {
    // 声明为final方法，避免子类覆写
    public final void templateMehthod() {
        this.step1();
        this.step2();
        this.step3();
    }

    protected void step1() {
        System.out.println("AbstractClass:step1");
    }

    protected void step2() {
        System.out.println("AbstractClass:step2");
    }

    protected void step3() {
        System.out.println("AbstractClass:step3");
    }
}
