package cn.designpattern.proxy;
/** 
 * @author whz 
 * @version on:2019-3-6 下午11:34:43 
 */
public class Marry {
	public static void main(String[] args) {
		//我需要代理
		IProxyMarry iMarry = new MyMarry();
		//将代理交给代理商
		IProxyMarry company = new WeddingCompany(iMarry);
		//代理商完成代理事情
		company.marry();
	}

}
/*
 * 我作为需要代理的用户
 */
class MyMarry implements IProxyMarry{

	@Override
	public void marry() {
		System.out.println("我们结婚啦！");
	}

}
/*
 * 婚庆公司作为代理
 */
class WeddingCompany implements IProxyMarry{
	private IProxyMarry proxyMarry;
	public WeddingCompany(IProxyMarry proxyMarry) {
		super();
		this.proxyMarry = proxyMarry;
	}

	@Override
	public void marry() {
		System.out.println("我们是婚庆公司。");
		System.out.println("婚礼前准备……");
		proxyMarry.marry();
		System.out.println("婚礼后工作……");
	}
}