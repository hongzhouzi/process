package cn.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author whz 
 * @version on:2019-3-5 下午11:52:31 
 */
public class Girl {
	List<IBoy> boys = new ArrayList<>();

	public Girl() {	}
	public void addBoys(IBoy boy) {
		boys.add(boy);
	}
	
	public void notifyBoy() {
		for(IBoy boy : boys){
			boy.getMessage("你们周末有空吗？");
		}
	}
}
