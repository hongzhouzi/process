package cn.interview.base;

public class Reflection {
    public static void main(String[] args) {
        //------------------get class-start-----------------------
        //1 通过forName方式获取Class
        Class c1 = null;
        try {
            c1 = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2 类名.class
        Class c2 = String.class;
        //3 Object类的getClass()获取
        String s = "";
        Class c3 = s.getClass();
        //------------------get class-end-----------------------

        c1.getPackage();
    }
}
