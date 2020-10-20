package cn.gp.designpattern.i.adapter.passport;

/**
 * @author hongzhou.wei
 * @date 2020/10/20
 */
public interface ILoginAdapter {
    /**
     * 判断是否兼容
     *
     * @param object
     * @return
     */
    boolean support(Object object);

    /**
     * 登录
     *
     * @param id
     * @param adapter
     * @return
     */
    Object login(String id, Object adapter);
}
