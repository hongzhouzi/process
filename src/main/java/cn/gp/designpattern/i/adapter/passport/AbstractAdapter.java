package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public abstract class AbstractAdapter extends PassportService implements ILoginAdapter {
    protected Object loginForRegist(String username, String password){
        if(null == password){
            password = "THIRD_EMPTY";
        }
        return super.login(username,password);
    }
}