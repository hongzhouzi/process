package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public interface IPassportForThird {

    Object loginForQQ(String openId);

    Object loginForWechat(String openId);

    Object loginForToken(String token);


}
