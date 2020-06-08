package cn.designpattern.singleton;

/** 
 * @author whz 
 * @version on:2019-3-5 下午08:36:03 
 */
public class Singleton {
	public static void main(String[] args) {
		//1、懒汉式
		SingletonLazy singletonLazy=SingletonLazy.getInstance();
		singletonLazy.whateverMethod();
		
		//2、饿汉式
		SingletonHungry singletonHungry=SingletonHungry.getInstance();
		singletonHungry.whateverMethod();
		
		//枚举
		SingletonEnum singletonEnum=SingletonEnum.INSTANCE;
		singletonEnum.whateverMethod();
	}
}

/**
 * 1、懒汉式：需要实例时才创建，当程序第一次访问单件模式实例时才进行创建。
 */
class SingletonLazy{
	private static SingletonLazy singleton;
	
	//关键是将构造函数私有化，外界不能创建对象只能通过getInstance()获得实例
	private SingletonLazy(){}
	
	//没有加锁线程不安全，加上synchronized线程才安全
	public static synchronized SingletonLazy getInstance() {
		if(singleton == null){
			singleton = new SingletonLazy();
		}
		return singleton;
	}
	public static SingletonLazy getInstance1() {
		//这儿是静态方法，不能用this钥匙，需要用SingletonLazy.class
		synchronized(SingletonLazy.class){
			if(singleton == null){
				singleton = new SingletonLazy();
			}
			return singleton;
		}
	}
	public void whateverMethod() {
		System.out.println("懒汉式，做其他操作的函数！");
	}
}

/**
 * 2、饿汉式：在程序启动或单件模式类被加载的时候，单件模式实例就已经被创建
 */
class SingletonHungry{
	private static SingletonHungry instance = new SingletonHungry();
	
	//关键是将构造函数私有化，外界不能创建对象只能通过getInstance()获得实例
	private SingletonHungry(){}
	
	//没有加锁线程不安全，加上synchronized线程才安全
	public static SingletonHungry getInstance() {
		return instance;
	}
	public void whateverMethod() {
		System.out.println("饿汉式，做其他操作的函数！");
	}
}
/**
 * 枚举：它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象，
 * 可谓是很坚强的壁垒啊，不过由于1.5中才加入enum特性，用这种方式写不免让人感觉生疏。
 */
enum SingletonEnum {
	INSTANCE;
	public void whateverMethod() {
		System.out.println("枚举，做其他操作的函数！");
	}
}