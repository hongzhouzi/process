package cn.java.base.annotation.readclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import cn.java.base.annotation.orm.MyField;
import cn.java.base.annotation.orm.MyTable;
import cn.java.base.annotation.orm.User;
/**
 * 
 * @author 帅气的泓舟老大！
 */

public class Demo {
	/**
	 * 模拟hibernate中根据注解生成sql语句
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
//	@Test
	public void name() throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		/*加载指定类
		 * Class对象表示封装一些数据，一个类被加载后JVM会创建一个对应类的Class对象，类的整个结构信息会被放到对应的Class对象中
		 * 这个对象就像一面镜子，通过这个镜子可以看到对应类的全部信息。一个类只对应一个Class对象（）
		 */
		Class clazz = Class.forName("cn.java.base.annotation.orm.User");
		Class clazz1 = Class.forName("cn.java.base.annotation.orm.User");
		System.out.println(clazz==clazz1);//结果为true
//		获取该类所有注解
		Annotation[] annotations=clazz.getAnnotations();
		for(Annotation a : annotations){
			System.out.println(a);
		}
//		获得该类中指定类注解
		MyTable table = (MyTable) clazz.getAnnotation(MyTable.class);
		System.out.println(table.value());
		
//		获得该类中指定属性注解
		Field f = clazz.getDeclaredField("name");
		MyField myField = f.getAnnotation(MyField.class);
		System.out.println(myField.columnName()+","+myField.type()+","+myField.length());
		
//		根据获得的表名，字段信息等拼接相关sql语句
	}
	/**
	 * 通过反射获取类中的属性，方法，构造器等信息
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
//	@Test
	public void fun1() throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException{
		Class clazz = Class.forName("cn.java.base.annotation.orm.User");
		
//		获得类的名字
		System.out.println(clazz.getName());//包名+类名
		System.out.println(clazz.getSimpleName());//类名
		
//		获得属性名
		Field[] fields1 = clazz.getFields();//只能获得public的属性
		Field[] fields2 = clazz.getDeclaredFields();//获得所有属性
		Field field = clazz.getDeclaredField("name");//获得指定属性
		System.out.println(field);
		
//		获得方法信息
		Method[] methods =clazz.getMethods();
		for(Method m:methods){
			System.out.println(m);
		}
		Method method2=clazz.getDeclaredMethod("setName", String.class);//后面指定参数类型，若空参就写null
		System.out.println(method2);
		
//		获得构造器信息
		Constructor[] constructors=clazz.getDeclaredConstructors();//所有构造器
		Constructor constructor=clazz.getDeclaredConstructor(null);//获得无参构造器
//		Constructor constructor1=clazz.getDeclaredConstructor(int.class,String.class);//指定参数构造器
		for(Constructor c: constructors){
			System.out.println("构造器："+c);
		}
	}
	
	/**
	 * 通过
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchFieldException 
	 */
	@Test
	public void f1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<User> clazz = (Class<User>) Class.forName("cn.java.base.annotation.orm.User");
		
//		通过反射API调用构造方法，构造对象
		User u = clazz.newInstance();//调用了User的无参构造方法
		System.out.println(u);
		//调用User的有参构造（需要指定参数类型）
		Constructor<User> c=clazz.getDeclaredConstructor(int.class,String.class,int.class);
		User u1=c.newInstance(11111,"whz",22);
		System.out.println(u1);
		
//		通过反射API调用普通方法
		User u3=clazz.newInstance();
		Method method=clazz.getDeclaredMethod("setName", String.class);
		method.invoke(u3, "whz666");
		System.out.println(u3.getName());
		
//		通过反射API操作属性
		User u4=clazz.newInstance();
		Field field=clazz.getDeclaredField("name");
		field.setAccessible(true);//这个属性不用做安全检查，可以直接访问，不然私有属性不能被访问到
		field.set(u4, "whz888");//通过反射直接写属性
		System.out.println(u4.getName());//通过反射直接读属性
		
	}
}
