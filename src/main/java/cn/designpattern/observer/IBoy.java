package cn.designpattern.observer;
/** 
 * @author whz 
 * @version on:2019-3-5 下午10:46:14 
 */
public interface IBoy {
	//小王和小李通过这个接口可以接收到小美发过来的消息
	public void getMessage(String msg);
}
