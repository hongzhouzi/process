package cn.java.base.genericity;

import java.util.List;

//public class GenImpl<T> implements IGen<T> {
public class GenImpl<T> implements IGen {
    /*@Override
    public T get() {
        return null;
    }*/
/*
多行注释
 */

    /**
     * 文档注释
     *
     * @param <T>
     * @return
     */

    public <T> List<T> func() {
        return null;
    }

    @Override
    public Object get() {
        return null;
    }


}
