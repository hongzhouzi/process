package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public class PassportForThirdAdapter implements IPassportForThird {

    @Override
    public Object loginForQQ(String openId) {
        return processLogin(openId, LoginForQQAdapter.class);
    }

    @Override
    public Object loginForWechat(String openId) {

        return processLogin(openId, LoginForWechatAdapter.class);

    }

    @Override
    public Object loginForToken(String token) {

        return processLogin(token, LoginForTokenAdapter.class);
    }


    private Object processLogin(String id,Class<? extends ILoginAdapter> clazz){
        try {
            ILoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)){
                return adapter.login(id,adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
