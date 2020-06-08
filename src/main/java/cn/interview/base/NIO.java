package cn.interview.base;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NIO {
    public static void main(String[] args) {
        String s = "abcde";
        //1. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("--------------allocate------------");
        see(buf);

        //2. 利用put将数据存入缓冲区中
        buf.put(s.getBytes());
        System.out.println("--------------put------------");
        see(buf);

        //3. 用flip切换至读取数据模式
        buf.flip();
        System.out.println("--------------flip------------");
        see(buf);

        //4. 用get读取缓冲区中的数据
        byte [] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst,0,dst.length));
        System.out.println("--------------get------------");
        see(buf);

        //5. 用rewind：可重复读
        buf.rewind();
        System.out.println("--------------rewind------------");
        see(buf);

        //6. 用clear：清空缓存区，但缓存区数据依然存在，但出于“被遗忘”状态
        buf.clear();
        System.out.println("--------------clear------------");
        see(buf);

    }
    static void see(Buffer buf){
        System.out.println("position:"+buf.position());
        System.out.println("limit:"+buf.limit());
        System.out.println("capacity:"+buf.capacity());
    }
}
