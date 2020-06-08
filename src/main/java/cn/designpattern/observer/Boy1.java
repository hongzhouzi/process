package cn.designpattern.observer;
/** 
 * @author whz 
 * @version on:2019-3-5 下午10:47:40 
 */
public class Boy1 implements IBoy {
	private String name="Boy1";
	public Boy1(String name) {
		super();
		this.name = name;
	}
	@Override
	public void getMessage(String msg) {
		System.out.println(name+"接到消息，消息是："+msg);
	}
}
