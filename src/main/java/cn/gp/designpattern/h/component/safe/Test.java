package cn.gp.designpattern.h.component.safe;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Test {
    public static void main(String[] args) {
        // 来一个根节点
        Composite root = new Composite("root");
        // 来一个树枝节点
        Composite branchA = new Composite("---branchA");
        Composite branchB = new Composite("------branchB");
        // 来一个叶子节点
        AbsComponent leafA = new Leaf("------leafA");
        AbsComponent leafB = new Leaf("---------leafB");
        AbsComponent leafC = new Leaf("---leafC");

        root.addChild(branchA);
        root.addChild(leafC);
        branchA.addChild(leafA);
        branchA.addChild(branchB);
        branchB.addChild(leafB);

        String result = root.operation();
        System.out.println(result);
    }
}
