package cn.gp.distributed.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端
 * @author hongzhou.wei
 * @date 2021/1/4
 */
public class NewIoServer {
    private static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // selector 必须是非阻塞的
            serverSocketChannel.configureBlocking(false);
            // 绑定地址
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            // 把【接收】事件注册到多路复用器上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 阻塞机制
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
               Iterator<SelectionKey> iterable=selectionKeys.iterator();
                while(iterable.hasNext()){
                    SelectionKey key=iterable.next();
                    iterable.remove();
                    if(key.isAcceptable()){
                        // 连接事件
                        handleAccept(key);
                    }else if(key.isReadable()){
                        // 读的就绪事件
                        handleRead(key);
                    }
                }
                /*selectionKeys.forEach(key -> {
                    if (key.isAcceptable()) {
                        // 处理接收事件
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        // 读的就绪事件
                        handleRead(key);
                    }
                });*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAccept(SelectionKey selectionKey) {
        ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();

        try {
            // 获取连接，一定会有一个
            SocketChannel accept = serverChannel.accept();
            // 设置非阻塞
            accept.configureBlocking(false);
            // 写入响应数据
            accept.write(ByteBuffer.wrap("Hello Client, I'm NIO Server".getBytes()));
            // 注册读事件
            accept.register(selector, SelectionKey.OP_READ);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleRead(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        // 分配缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            // 读取值，但这儿不一定有值
            channel.read(byteBuffer);
            System.out.println("server receive msg:" + new String(byteBuffer.array()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
