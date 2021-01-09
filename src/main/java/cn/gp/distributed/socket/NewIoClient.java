package cn.gp.distributed.socket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 客户端
 * @author hongzhou.wei
 * @date 2021/1/4
 */
public class NewIoClient {
    private static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();

            SocketChannel socketChannel = SocketChannel.open();
            // selector 必须是非阻塞的
            socketChannel.configureBlocking(false);
            // 连接到指定地址
            socketChannel.connect(new InetSocketAddress("localhost", 8080));
            // 把【连接】事件注册到多路复用器上
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                // 阻塞机制
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        //连接事件
                        handleConnect(key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    }
                }
                /*selectionKeys.forEach(key -> {
                    if (key.isConnectable()) {
                        // 处理连接事件
                        handleConnect(key);
                    } else if (key.isReadable()) {
                        // 处理读的就绪事件
                        handleRead(key);
                    }
                });*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnect(SelectionKey selectionKey) {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            if (channel.isConnectionPending()) {
                channel.finishConnect();
            }

            // 设置非阻塞
            channel.configureBlocking(false);
            // 写入响应数据
            channel.write(ByteBuffer.wrap("Hello Server, I'm NIO Client".getBytes()));
            // 注册读事件
            channel.register(selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRead(SelectionKey selectionKey) {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            // 分配缓冲区大小
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 读取值，但这儿不一定有值
            channel.read(byteBuffer);
            System.out.println("Client receive msg:" + new String(byteBuffer.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void f() throws IOException {
        File file = new File("test.zip");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("", 1234));
        // 直接使用了transferTo()进行通道间的数据传输
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);
    }
}
