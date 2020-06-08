package cn.designpattern.singleton;
/** 
 * @author whz 
 * @version on:2019-3-5 下午09:24:03 
 */
public class SingletonInStaticClass {
	//内部静态类
	private static class SingletonHolder {
		private static final SingletonInStaticClass INSTANCE = new SingletonInStaticClass();
	}
	//将构造函数私有化
	private SingletonInStaticClass(){}
	//获取：获取内部类中的实例
	public static final  SingletonInStaticClass getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
