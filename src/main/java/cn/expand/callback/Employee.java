package cn.expand.callback;
/** 
 * @author whz 
 * @version on:2019-2-28 上午09:44:58 
 */
public class Employee {
/*//	Employee中声明回调接口，在构造函数中传入回调参数
	Callback callback;
	public Employee(Callback callback) {
		this.callback = callback;
	}
	*/
	public Employee(){}
//	完成boss交代的事情
	public void doSomething(Callback callback, String event) {
		String flag=null;
		System.out.println("Employee:正在做："+event);
		
//		根据完成情况通知Boss，即接口回调
		System.out.println("Employee:完成了任务！");
		flag="完成";
		if(flag.equals("完成")){
			callback.yes();
		}
		
		System.out.println("Employee:未完成任务！");
		flag="未完成";
		if(flag.equals("未完成")){
			callback.no();
		}
		
	}
}
