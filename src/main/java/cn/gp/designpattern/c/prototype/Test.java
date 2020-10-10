package cn.gp.designpattern.c.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongzhou.wei
 * @date 2020/10/10
 */
public class Test {
    public static void main(String[] args) {
        ProtoTypeEntity entity = new ProtoTypeEntity();
        entity.setAge(15);
        entity.setName("zz");
        List<String> hobbies = new ArrayList<>();
        hobbies.add("唱");
        hobbies.add("跳");
        hobbies.add("rap");
        hobbies.add("篮球");
        entity.setHobbies(hobbies);

        System.out.println("原型对象：" + entity);
        ProtoTypeEntity copy = entity.clone();
        copy.getHobbies().add("copy");

        ProtoTypeEntity deep = entity.deepCloneByJson();
        deep.getHobbies().add("deep");

        System.out.println("原型对象：" + entity);
        System.out.println("浅复制对象：" + copy);
        System.out.println("深复制对象：" + deep);
        System.out.println("浅复制对象与原型对象的属性引用相等 "+ (entity.getHobbies() == copy.getHobbies()));
        System.out.println("深复制对象与原型对象的属性引用相等 "+ (entity.getHobbies() == deep.getHobbies()));
    }
}
