package cn.gp.distributed.serializer;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 测试类
 * @author hongzhou.wei
 * @date 2021/1/4
 */
public class SerializableDemoTest {


    @Test
    public void serializableDemo() {
        JavaSerializer serializer = new JavaSerializer();
        User user = new User();
        user.setName("whz");
        user.setAge(18);

        byte[] bytes = serializer.serialize(user);
        for (byte aByte : bytes) {
            System.out.print(aByte + " ");
        }
        System.out.println();
        User userRever = serializer.deserialize(bytes);
        System.out.println(userRever);
    }

    @Test
    public void serializable() {
        try {
            User user = new User();
            user.setName("whz");
            user.setAge(18);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("user"));
            outputStream.writeObject(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserializable() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("user")));
            User userRever = (User) objectInputStream.readObject();
            System.out.println(userRever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
