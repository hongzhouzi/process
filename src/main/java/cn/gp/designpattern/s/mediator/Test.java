package cn.gp.designpattern.s.mediator;

/**
 * @author hongzhou.wei
 * @date 2020/10/18
 */
public class Test {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        ConcreteColleagueA colleagueA = new ConcreteColleagueA(mediator);
        ConcreteColleagueB colleagueB = new ConcreteColleagueB(mediator);
        colleagueA.depMethodA();
        System.out.println("-------------------------");
        colleagueB.depMethodB();
    }
}
