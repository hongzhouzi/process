package cn.expand.callback;
/** 
 * @author whz 
 * @version on:2019-2-28 上午09:41:24 
 */
//Boss需要实现回调的接口
public class Boss implements Callback{
//	传入一个员工对象
	private Employee employee;
	public Boss(Employee employee) {
		this.employee = employee;
	}
	
//	告诉员工要做什么事情
	public void doEvent(String event) {
		System.out.println("Boss："+event);
//      将当前对象传给员工，员工才可以回调
		employee.doSomething(this, event);
	}
	
//	员工完成事情后回调的方法
	@Override
	public void yes() {
		System.out.println("Boss：任务完成得很不错，下个月给你涨工资");
	}
	@Override
	public void no() {
		System.out.println("Boss：任务没完成，好好反思一下");
	}
	
}
