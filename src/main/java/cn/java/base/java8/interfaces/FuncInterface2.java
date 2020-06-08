package cn.java.base.java8.interfaces;

@FunctionalInterface
public interface FuncInterface2<T, R> {
    R getValue(T t1, T t2);
}
