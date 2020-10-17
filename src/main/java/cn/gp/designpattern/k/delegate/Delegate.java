package cn.gp.designpattern.k.delegate;

import java.util.Random;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public class Delegate implements Task{
    @Override
    public void doTask() {
        System.out.println("开始分配任务");
        Task task = null;
        // 根据不同情况，将任务交个不同真正干活的执行
        if (new Random().nextBoolean()){
            task = new ConcreteA();
        }else{
            task = new ConcreteB();
        }
        task.doTask();
        System.out.println("结束分配任务");
    }
}
