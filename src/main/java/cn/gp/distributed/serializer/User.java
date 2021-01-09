package cn.gp.distributed.serializer;

/**
 * 测试的用户实体类
 * @author hongzhou.wei
 * @date 2021/1/4
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {

    private static final long   serialVersionUID = 11L;
    private              String name;
    private              String tel;

    private transient int age;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // 写入，有transient关键字修饰的int类型字段
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // 读取，有transient关键字修饰的int类型字段
        age = in.readInt();
    }

    public int getAge() {
        return age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
            "name='" + name + '\'' +
            ", tel='" + tel + '\'' +
            ", age=" + age +
            '}';
    }
}
