package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public class Test {

    public static void main(String[] args) {
        IPassportForThird adapter = new PassportForThirdAdapter();
        adapter.loginForQQ("sdfasdfasfasfas");
    }
}
