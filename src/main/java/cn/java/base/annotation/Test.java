package cn.java.base.annotation;

@Annotation
public class Test {
	@Annotation(name = "")
	public void name() {
		String name="nn";
		System.out.println(name);
	}
}
