package cn.designpattern.decorator;
/** 
 * @author whz 
 * @version on:2019-3-6 上午12:23:32 
 */
public class Food {
	private String name;
	
	public Food(){}
	public Food(String name) {
		super();
		this.name = name;
	}
	
	public String make() {
		return name;
	}
}
