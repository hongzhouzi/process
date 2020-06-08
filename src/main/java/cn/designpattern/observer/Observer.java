package cn.designpattern.observer;
/** 
 * @author whz 
 * @version on:2019-3-5 下午09:37:15 
 * 观察者模式：
 * 		对象间一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * 直观理解：
 * 		假设有三个人，小美（女，22），小王和小李。小美很漂亮，小王和小李是两个程序猿，时刻关注着小美的一举一动。
 * 		有一天，小美说了一句：“谁来陪我打游戏啊。”这句话被小王和小李听到了，结果乐坏了，没一会儿，小王就冲到小美家门口了
 * 		在这里，小美是被观察者，小王和小李是观察者，被观察者发出一条信息，然后观察者们进行相应的处理
 */
public class Observer {
	public static void main(String[] args) {
		Girl girl = new Girl();
		Boy1 boy1 = new Boy1("Boy1");
		Boy1 boy2 = new Boy1("Boy2");
		//男孩在女孩哪儿注册一下，女孩有事儿才能通知男孩
		girl.addBoys(boy1);
		girl.addBoys(boy2);
		//女孩发送通知
		girl.notifyBoy();
	}
	
}
