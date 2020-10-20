package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public class LoginForQQAdapter extends AbstractAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public Object login(String id, Object adapter) {
        if (!support(adapter)) {
            return null;
        }
        //accesseToken
        //time
        return super.loginForRegist(id, null);
    }
}