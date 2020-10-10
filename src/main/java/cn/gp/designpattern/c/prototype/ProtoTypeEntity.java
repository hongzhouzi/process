package cn.gp.designpattern.c.prototype;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongzhou.wei
 * @date 2020/10/10
 */
public class ProtoTypeEntity implements Cloneable,Serializable {
    Integer age;
    String name;
    List<String> hobbies;



    /**
     * @return 浅克隆-存引用的复制的引用，没有复制值
     */
    @Override
    public ProtoTypeEntity clone()  {
//        return this;
        try {
            return (ProtoTypeEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return 深克隆-有引用的也复制的引用对应的值
     */
    public ProtoTypeEntity deepClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            return (ProtoTypeEntity) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ProtoTypeEntity deepCloneByJson() {
        return JSON.parseObject(JSON.toJSONString(this), ProtoTypeEntity.class);
//        return JSON.toJavaObject((JSON) JSONObject.toJSON(this), ProtoTypeEntity.class);
    }




    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "ProtoTypeEntity{" +
            "age=" + age +
            ", name='" + name + '\'' +
            ", hobbies=" + hobbies +
            '}';
    }
}
