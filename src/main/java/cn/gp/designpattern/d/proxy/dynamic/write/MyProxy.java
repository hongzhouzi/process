package cn.gp.designpattern.d.proxy.dynamic.write;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成代理源代码的工具类
 *
 * @author hongzhou.wei
 * @date 2020/10/3
 */
public class MyProxy {
    public static final String ln = "\r\n";

    public static Object newProxyInstance(MyClassLoader classLoader, Class<?> [] interfaces, MyInvocationHandler h){
        try {
            // 1、动态生成源代码.java文件
            String src = generateSrc(interfaces);

            // 2、Java文件输出磁盘
            File f = write2Desk(src);

            //3、把生成的.java文件编译成.class文件
            compiler(f);

            //4、编译生成的.class文件加载到JVM中来
            // $Proxy0 和生成类时类名保持一致即可，生成的临时类放在本类同路径下
            // 所以这儿要自定义ClassLoader，在加载临时类时就在当前同路径下找的
            Class proxyClass =  classLoader.findClass("$Proxy0");
            // 注意：构造器中要传入调用处理器
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
            f.delete();

            //5、返回字节码重组以后的新的代理对象
            // 注意：构造器中要传入调用处理器
            return c.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成代理类的源代码
     *
     * @param interfaces
     * @return
     */
    private static String generateSrc(Class<?>[] interfaces){
        StringBuffer sb = new StringBuffer();
        // 包相关
        sb.append(MyProxy.class.getPackage() + ";" + ln);
        sb.append("import " + interfaces[0].getName() + ";" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        // 定义类并实现接口
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        // 初始化变量
        sb.append("MyInvocationHandler h;" + ln);
        sb.append("public $Proxy0(MyInvocationHandler h) { " + ln);
        sb.append("this.h = h;");
        sb.append("}" + ln);
        // 实现接口中的方法
        for (Method m : interfaces[0].getMethods()){
            Class<?>[] params = m.getParameterTypes();

            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();

            // 处理接口方法中的参数（准备着，下一步用）
            for (int i = 0; i < params.length; i++) {
                Class clazz = params[i];
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName());
                paramNames.append(type + " " +  paramName);
                paramValues.append(paramName);
                paramClasses.append(clazz.getName() + ".class");
                if(i > 0 && i < params.length-1){
                    paramNames.append(",");
                    paramClasses.append(",");
                    paramValues.append(",");
                }
            }

            // 处理具体方法
            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames.toString() + ") {" + ln);
            sb.append("try{" + ln);
            sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + ln);
            // 处理正常情况下的返回值，返回调用处理器调用的接口（代理的方法主要就在重写的调用处理器的接口方法中，这儿只处理的接口中的方法）
            sb.append(((m.getReturnType() != void.class) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})",m.getReturnType()) + ";" + ln);
            // 处理异常情况
            sb.append("}catch(Error _ex) { }");
            sb.append("catch(Throwable e){" + ln);
            sb.append("throw new UndeclaredThrowableException(e);" + ln);
            sb.append("}");
            sb.append(getReturnEmptyCode(m.getReturnType()));
            sb.append("}");
        }
        sb.append("}" + ln);
        return sb.toString();
    }

    /**
     * 将内容输出到磁盘
     *
     * @param src 源文件内容
     * @return {@link File} 文件信息
     * @throws IOException
     */
    private static File write2Desk(String src) throws IOException {
        String filePath = MyProxy.class.getResource("").getPath();
        File f = new File(filePath + "$Proxy0.java");
        FileWriter fw = new FileWriter(f);
        fw.write(src);
        fw.flush();
        fw.close();
        return f;
    }

    /**
     * 把生成的.java文件编译成.class文件
     *
     * @param f
     * @throws IOException
     */
    private static void compiler(File f) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manage = compiler.getStandardFileManager(null,null,null);
        Iterable iterable = manage.getJavaFileObjects(f);

        JavaCompiler.CompilationTask task = compiler.getTask(null,manage,null,null,null,iterable);
        task.call();
        manage.close();
    }



    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
        mappings.put(String.class,String.class);
        mappings.put(double.class,Double.class);
        mappings.put(float.class,Float.class);
    }

    /**
     * 出现异常时返回的内容
     *
     * @param returnClass
     * @return
     */
    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    /**
     * 将类名的首字母转为小写
     *
     * @param src
     * @return
     */
    private static String toLowerFirstCase(String src){
        char [] chars = src.toCharArray();
        if(65 <= chars[0] && chars[0] <= 90) {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }
}
