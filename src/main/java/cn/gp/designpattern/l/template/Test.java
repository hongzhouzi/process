package cn.gp.designpattern.l.template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args) {
        AbstractClass abc = new ConcreteClassA();
        abc.templateMehthod();
    }
}
