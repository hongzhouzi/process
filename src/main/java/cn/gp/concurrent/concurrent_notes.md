#### 前言

> 设计模式最重要的是**解耦**，学习过程主要学习设计模式是如何**总结经验并将经验为自己所用**，同时锻炼将业务需求转换技术实现的一种有效方式。

## 多线程基础

#### 线程的启动

new Thread.start(); // 启动线程

run(); // 直接调用方法

> Java是不提供线程的，只是对底层语言进行了封装，调用start方法本质是在调用本地start0()方法启动线程，JVM再根据不同的操作系统创建对应操作系统上的线程再启动线程，轮到该线程执行时（等待操作系统调度算法选中）再从操作系统层面调用JVM中的run方法，JVM中的run方法再调用Java高级语言写的run方法。执行完后再由JVM销毁该线程。

![调用start](.\images\1.调用start.png)

#### 线程的终止

> 由于线程时异步执行的，终止时不受控制，若强制执行则不安全，可能导致数据丢失。
>
> 
>
> interupte()本质是通过一个线程共享变量来实现线程间的通信，底层先设置中断状态（一个共享变量），再唤醒处于阻塞状态的线程（若不唤醒则该线程一直处于阻塞状态就无法响应中断）。
>
> 之所以那些阻塞状态的操作（如sleep、wait、join……）都需要抛出异常，就是因为有可能有线程来中断它，它就可以在中断异常中对中断做出处理。是否响应中断由被中断线程决定。
>
> 线程在InterruptedException中对中断进行处理
>
> ![线程中断](.\images\2.线程中断.png)

#### 线程状态转化

> 创建线程后为初始状态【**NEW**】，调用start方法启动线程后就绪，当该线程被系统调度选中后变为运行中，就绪和运行中都为运行状态【**RUNNABLE**】，若此时调用了阻塞线程的方法（Object.wait()、Object.join()、LockSupport.park()）后变成等待状态【**WAITING**】，若调用的阻塞线程的方法中指定了时间（Thread.sleep(long)、Object.wait(long)、Object.join(long)、LockSupport.parkNanos()、LockSupport.parkUntil()）就变成超时等待状态【**TIMED_WAITING**】,直到调用了唤醒线程的方法（Object.notify()、Object.notifyAll()、LockSupport.unpark(Thread)）或者阻塞时间到了线程又进入运行状态，当多个线程在竞争同一把锁时没有竞争赢的线程就进入阻塞状态【**BLOCKING**】，若线程执行完了就进入终止状态【**TERMINATED**】。
>
> 1、NEW(初始状态)：高级语言层面的线程创建，操作系统并没有真正创建线程，因此也不会获得CPU的执行权，通过调用start()方法进入RUNNABLE状态；
> 2、RUNNABLE(可运行/运行状态）：RUNNABLE状态分可运行和运行两种状态，在java语言中统一叫做RUNNABLE状态，NEW状态的线程通过调用start()方法进入可运行状态，此时操作系统会新建一个线程，当有CPU空闲时，OS会分配CPU执行权，此时进入运行状态。运行时状态也可通过调用yield()方法再次进入可运行状态；
> 3、BLOCKED(阻塞状态）：RUNNABLE状态的线程如果遇到同步锁等待的情况，会进入BLOCKED状态，此时会让出CPU执行权。若同步锁释放，当前线程重新获取CPU执行权并获取锁，此时会从BLOCKED状态重新进入RUNNABLE状态；
> 4、WAITING(无限时等待）：RUNNABLE状态的线程内部调用wait()、sleep()、join()、LockSupport.part()等方法时，会从RUNNABLE状态进入WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 5、TIMED_WAITING(有限时等待）：RUNNABLE状态的线程内部调用wait(long)、sleep(long)、join(long)、LockSupport.partUnit(xx)方法时，会从RUNNABLE状态进入TIMED_WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 6、TERMINATED(终止）：线程运行结束，进入终止状态。也可以通过调用stop()或interrupt()方法进入终止状态，不过stop()方法会直接杀死线程，当前线程持有的锁也不会释放，该方法时不可取的，interrupt()方法仅是通知线程可以终止，具体是否要终止有线程内部自己决定。interrupt()方法会更新线程标识位，并且抛出InterruptedException，线程内部可以通过主动检查线程标志位或捕获InterruptException的方式来获取线程终止的通知。



#### 应用场景

> - 文件等资源上传业务
>
> - 系统间数据同步操作、数据解析、原始消息文件入库操作。
>
> - 系统中的日志记录。
>
> - 发送短信、推送通知、用户注册后送积分。
>
>   参与过的项目中数据同步操作用到了多线程同步数据。
>
>   ```java
>   private static final ThreadPoolTaskExecutor EXECUTOR = SpringUtil.getBean("threadPoolTaskExecutor");
>
>   TransactionSynchronizationManager
>       .registerSynchronization(new TransactionSynchronizationAdapter() {
>           @Override
>           public void afterCommit() {
>               EXECUTOR.execute(() -> {
>                   try {
>   dynaDataClientService.upload(ModuleCodeEnum.STUDENT_BASIC.getModule(), basicId);
>                   } catch (Throwable e) {
>                       log.error("推送学生数据失败:{}", e.getMessage());
>                   }
>               });
>           }
>       });
>   ```
>
>

## 并发

#### 锁

> **本质：** 共享资源。
>
> **使用：** 修饰方法层面、修饰代码块层面。使用时根据控制的粒度选择。
>
> ```java
>     /**
>      * 修饰方法
>      */
>     synchronized void demo(){
>         // 临界区
>     }
>
>     /**
>      * 修饰代码块
>      */
>     Object obj = new Object();
>     void demo1(){
>         synchronized (obj){
>             // 临界区
>         }
>     }
> ```
>
> **作用范围：** 
>
> synchronized有3种方式来加锁，不同的修饰类型代表锁的控制粒度。
>
> 1. 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁。
> 2. 静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁。
> 3. 修饰代码块，可指定实例或类作为加锁对象，进入同步代码块钱要获得给定对象的锁。
>
> 类锁：方法加了static修饰的、XX.class，静态锁、类对象；资源供多个对象访问时就需要加类锁，针对所有对象都互斥。
>
> 实例锁：对象实例，只针对当前对象实例有效。锁this、指定对象、没有static修饰的方法。
>
> 锁的作用范围主要看锁的实例还是类，再看临界区。锁的作用范围是由对象的生命周期决定的（如实例的实例化和销毁在某个代码块内有效，类的整个运行过程有效）。类锁作用范围最大。
>
> ![3.锁的作用范围](.\images\3.锁的作用范围.png)
>
> **锁的互斥性：** 
>
> 在锁实例对象时要锁同一个实例，若锁的不同实例那么锁就没有意义了。当多个线程访问资源时，看访问时锁住的是不是同一个东西，是同一个东西锁才是互斥的。类锁和对象锁主要的差异也就是锁的互斥范围。

#### 锁的存储（对象头）

##### 对象头存储在内存中的布局



![4.锁的存储对象头-存储布局](.\images\4.锁的存储对象头-存储布局.png)

##### 32位和64位存储的差异

![5.锁的存储对象头-32位64位差异](.\images\5.锁的存储对象头-32位64位差异.png)

##### 打印加锁类查看对象头

1. **引入工具依赖**

   ```xml
   <dependency>
       <groupId>org.openjdk.jol</groupId>
       <artifactId>jol-core</artifactId>
       <version>0.10</version>
   </dependency>
   ```


2. **使用工具**

   ```java
   public static void main(String[] args) {
       Test classLayout = new Test();
       synchronized (classLayout){
           System.out.println("locking");
           // 打印对象头，value中展示的二进制和十六进制是按照大端存储模式打印出来的，转成十进制的数据应该从后往前计算每组
           // 大端存储模式：指一个数据的低位字节序的内容放在高地址处，高位字节序存的内容放在低地址处。
           System.out.println(ClassLayout.parseInstance(classLayout).toPrintable());
       }
   }
   ```

   **输出内容**

   ```
   cn.gp.concurrent.Test object internals:
    OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
         0     4                    (object header)                           08 f8 86 01 (00001000 11111000 10000110 00000001) (25622536)
         4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4                    (object header)                           05 c1 00 20 (00000101 11000001 00000000 00100000) (536920325)
        12     4   java.lang.Object Test.obj                                  (object)
   Instance size: 16 bytes
   Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
   ```

   打印展示数据为大端存储模式下的数据，正常顺序是如下十六进制

   十六进制：0x 00000001 10000110 11111000 00001000

   二进制（64位）：看最后三位 000（轻量级锁，通过最后三位查看锁的标记和状态，上图中含义表实际上轻量级锁只通过后面2位判断，无锁和偏向锁主要通过后三位判断）


3. ​

#### 锁的升级

加锁是为了保护资源，与是否多线程没有直接的关系。

##### 偏向锁

> 在大多数情况下，锁不仅仅不存在多线程的竞争，而且总是由同一个线程多次获得。在这个背景下就设计了偏向锁。偏向锁顾名思义就是锁偏向某个线程。
>
> 当一个线程访问了同步锁的代码块时会在对象头中存储当前线程的ID，后续这个线程进入和退出这段加了同步锁的代码块时就**不需要再次加锁和释放锁**，而是**直接比较对象头里面是否存储了指向当前线程的偏向锁**。若相等表示偏向锁是偏向当前线程的就不需要再次尝试获得锁了。引入偏向锁是为了在无多线程竞争的情况下尽量**减少不必要的轻量级锁执行路径**。（偏向锁的目的是**消除数据在无竞争情况下的同步原语**，进一步提高程序的运行性能。）
>
> 偏向锁默认是关闭的，因为它只用作优化处理，当优化后遇到线程竞争的情况锁则会自动进行锁的升级。
>
> 打开偏向锁：VM options中输入
>
> ```xml
> -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
> ```
>
> ![6.偏向锁的撤销过程](.\images\6.偏向锁的撤销过程.png)

##### 轻量级锁

> 若**偏向锁被关闭**或**当前偏向锁被其他线程获取**，此时若有线程取抢占同步锁，那么锁会从偏向锁升级到轻量级锁。
>
> ![7.轻量级锁及膨胀过程](.\images\7.轻量级锁及膨胀过程.png)

##### 重量级锁

> - 多个线程竞争一个锁的时候，JVM会阻塞加锁失败的线程，并在模板锁被释放的时候唤醒这些线程。
> - Java中线程的阻塞和唤醒都是由OS完成的。
> - 升级为重量级锁的时候，锁标志状态值变为10，此时Mark Word中**存储的是指向重量级锁的指针**，等待锁的线程都会进入阻塞状态。
>
>   ![8.重量级锁](.\images\8.重量级锁.png)



> 每个对象都会与一个监视器monitor关联，我们可以把它理解成一把锁，当一个线程想要执行一段被synchronized修饰的同步方法或代码块时该线程先获取奥synchronized修饰的对象对应的monitor。
>
> monitorenter表示取获得一个对象监视器，monitorexit表示释放监视器的所有权，使得其他被阻塞的线程可以尝试去获得这个监视器。
>
> monitor依赖操作系统的MutexLock（互斥锁）来实现，线程被阻塞后便进入OS内核调度状态，这个会导致OS在用户态和内核态来回切换，严重影响锁的性能。



乐观锁概念

> CAS比较预期数据和原始数据是否一致，若一致则修改，不一致则修改失败。

##### 锁的升级过程

无锁升级到偏向锁只会有一次CAS，偏向锁升级到轻量级锁会有多次CAS（默认最多10次），之所以这儿用多次CAS（自旋锁，自适应自旋）而不是阻塞是因为线程从获得锁到释放锁过程的时间很短，通过多次CAS抢占锁的性能比阻塞抢占锁的性能更好。但经过10次（这是JDK1.6之前，1.6之后引入了自适应自旋：根据上一次抢占锁时间的长短来决定当前自旋的次数，上次抢占锁时间长这次自旋次数就短些，上次抢占锁时间段这次自旋时间就长些）还没抢占到锁就宣告自旋失败，就会升级为重量级锁。

#### 线程的通信（wait/notify）

notify/notifyAll，线程的通信都是基于重量级锁来实现的。

在java中提供了wait/notify这个机制用来实现条件等待和唤醒。以抢占锁为例，若线程A持有锁，线程B再去抢占锁时，它需要等待持有锁的线程释放之后才抢占，那么B怎么知道A什么时候释放呢？这个时候就可以采用通信机制。

![9.线程通信机制](.\images\9.线程通信机制.png)

##### 应用

> **生产者消费者模型**
>
> 1.阻塞队列（需对队列加锁同步）
> 2.生产者往队列中添加商品，若队列满了就wait()等待通知
> 3.消费者从队列中取出商品，若队列空了就notify()通知消费者生产



## volatile

锁的关键字，在OS系统总线层面加了锁，保证缓存中的内容一致。