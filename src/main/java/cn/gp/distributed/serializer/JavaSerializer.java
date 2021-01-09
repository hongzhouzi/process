package cn.gp.distributed.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Java序列化
 * @author hongzhou.wei
 * @date 2021/1/4
 */
public class JavaSerializer {

    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("user"));
            outputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public <T> T deserialize(byte[] data) {
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(data);
        try {
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("user")));
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}