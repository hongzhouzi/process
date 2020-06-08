package cn.designpattern.decorator;
/** 
 * @author whz 
 * @version on:2019-3-6 上午12:29:10 
 */
public class Vegetable {
	private Food basicFood;

	public Vegetable(Food basicFood) {
		super();
		this.basicFood = basicFood;
	}
	public String make() {
		return basicFood.make()+"蔬菜";
	}
}
