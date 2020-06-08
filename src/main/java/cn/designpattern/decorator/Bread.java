package cn.designpattern.decorator;
/** 
 * @author whz 
 * @version on:2019-3-6 上午12:25:37 
 */
public class Bread extends Food{
	private Food basicFood;

	public Bread(Food basicFood) {
		super();
		this.basicFood = basicFood;
	}
	@Override
	public String make() {
		return basicFood.make()+"面包";
	}
}
