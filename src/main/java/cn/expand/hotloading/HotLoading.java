package cn.expand.hotloading;
import java.net.URL;
import java.net.URLClassLoader;
/** 
 * @author whz 
 * @version on:2019-3-30 下午12:42:16 
 */
public class HotLoading {
	//在进行debug模式才能看到效果（直接运行看不到）
	public static void main(String[] args) throws Exception {
		//每2秒检测一次是否有更新
		while(true){
				//1、指定某class文件的存放目录（包括package）
				URL classUrl = new URL("file:G:\\JavaWeb\\code\\JavaStudy\\bin\\cn\\hotloading\\");
				
				//2、创建一个新的类加载器，并且给它指定一个目录
				URLClassLoader loader = new URLClassLoader(new URL[] {classUrl});
				
				//3、通过类加载器进行加载（有包名需要加上）
				Class<?> clazz = loader.loadClass("cn.expand.hotloading.Hello");
				
				//4、构造一个实例对象
				Object newInstance = clazz.newInstance();
				
				//5、调用getValue方法
				Object value = clazz.getMethod("HelloWord").invoke(newInstance);
				System.out.println("调用HelloWord获得的返回值为："+value);
				
				loader.close();
				Thread.sleep(2000L);
			
		}
	}
}
