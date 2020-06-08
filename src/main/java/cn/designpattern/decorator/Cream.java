package cn.designpattern.decorator;
/** 
 * @author whz 
 * @version on:2019-3-6 上午12:28:30 
 */
public class Cream {
	private Food basicFood;

	public Cream(Food basicFood) {
		super();
		this.basicFood = basicFood;
	}
	public String make() {
		return basicFood.make()+"奶油";
	}
}
