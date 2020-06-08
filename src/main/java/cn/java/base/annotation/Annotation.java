package cn.java.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//表示注解方法和类，若注解到其他地方会报错
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation {
	
//	表示在使用该注解时必须使用该属性，后面传了一个默认值，
	String name()default "";
	int age() default 0;
	
}
