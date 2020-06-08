package cn.examination.hikvision;

public class Singleton {
    private Singleton(){}
    //静态内部类创建单例类
    public Singleton getInstance(){
        return In.instance;
    }
    private static class In{
        private static final Singleton instance = new Singleton();
    }
}
