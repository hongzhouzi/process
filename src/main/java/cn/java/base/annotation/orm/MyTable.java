package cn.java.base.annotation.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTable {
//	默认写成value，用到时可以不用指明，不写成value用时需要指明
	String value();
}
