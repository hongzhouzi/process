package cn.expand.callback;


/** 
 * @author whz 
 * @version on:2019-2-28 上午09:56:36 
 */
public class Test {
	public static void main(String[] args) {
		Employee employee=new Employee();
		Boss boss = new Boss(employee);
		boss.doEvent("写hello world！");
	}
}
