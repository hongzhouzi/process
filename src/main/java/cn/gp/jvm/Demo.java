package cn.gp.jvm;

import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.openjdk.jol.info.ClassLayout;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JVM学习demo
 * @author hongzhou.wei
 * @date 2020/12/17
 */
public class Demo {


    public static void main(String[] args) {
    }

    static long count = 0L;

    @Test
    public void stackOverFlow() {
        System.out.println(count++);
        stackOverFlow();
    }


    @Test
    public void heapOutOfMemory() {
        List<Object> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(new Object());
            System.out.println(i++);
        }
    }

    @Test
    public void nonHeapOutOfMemory() {
        List<Class<?>> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(MyMetaspace.createClass());
            System.out.println(i++);
        }
    }

    @Test
    public void weakRef() {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        Thread thread = new Thread(() -> {
            try {
                int cnt = 0;
                WeakReference<byte[]> weakRef;
                while ((weakRef = (WeakReference) referenceQueue.remove()) != null) {
                    System.out.println(cnt++ + "回收了" + weakRef);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        Object obj = new Object();
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[1024 * 1024];
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, referenceQueue);
            map.put(weakReference, obj);
        }
        System.out.println(map.size());
    }

    @Test
    public void classLoader() {
        Demo obj = new Demo();
        // App ClassLoader
        System.out.println(obj.getClass().getClassLoader());
        // Extension ClassLoader
        System.out.println(obj.getClass().getClassLoader().getParent());
        // Bootstrap ClassLoader
        System.out.println(obj.getClass().getClassLoader().getParent().getParent());
    }

    @Test
    public void printLayout() {
        Object obj = new Object();
        System.out.println(obj);
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println(obj.hashCode());
    }

    @Test
    public void softReference() {
        // 业务代码……
        // 业务代码使用到了obj实例
        Object obj = new Object();

        // 使用完了 obj 将它设置为软引用类型，并释放强引用
        SoftReference<Object> sr = new SoftReference<>(obj);
        obj = null;

        // 下次使用 obj 时
        if (Objects.nonNull(sr)) {
            obj = sr.get();
        } else {
            // 若内存资源不足，GC 可能回收了 obj 的软引用，因此需要重新装载
            obj = new Object();
            sr = new SoftReference<>(obj);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private static class MyMetaspace extends ClassLoader {
        static Class<?> createClass() {
            ClassWriter cw = new ClassWriter(0);
            cw.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "Class", null, "java/lang/Object", null);
            MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mw.visitVarInsn(Opcodes.ALOAD, 0);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mw.visitInsn(Opcodes.RETURN);
            mw.visitMaxs(1, 1);
            mw.visitEnd();

            byte[] code = cw.toByteArray();

            return
                new MyMetaspace().defineClass("Class", code, 0, code.length);
        }
    }


}
