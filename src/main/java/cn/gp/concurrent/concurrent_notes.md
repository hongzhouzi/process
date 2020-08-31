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


3. 

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



## 线程安全性背后的本质

### 一个问题引发的思考

```java
private static boolean stop = false;
public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
        int i = 0;
        while (!stop) {
            i++;
            //                System.out.println("rs:" + i);
            /*try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
        }
    }).start();
    Thread.sleep(1000);
    stop = true;
}
```

**原因分析：**

以上代码运行后不能停止，看似在线程启动后1s对线程进行了停止操作，但实际上却停止不了。。。猜想一下会是什么原因导致的呢？线程里面停止不了无非就是线程中读到的stop变量一直是false，那么为什么明明改变了stop值读到的值却还是没有变化呢？于是大胆推测是缓存在作祟，毕竟CPU中设计了三级缓存，读到的值与实际值不同很大可能性是缓存不一致导致的。

**解决方式分析：**

1. **在while代码块中添加print。**

   a. println用到了**synchronized**同步关键字，释放锁的操作会强制把工作内存中涉及的写操作同步到主内存。这个同步会防止循环期间对于stop的缓存。（通过在while代码块中加synchronized关键字也可达到一样效果）

   c. println属于**IO操作？？？**，磁盘IO效率比CPU计算效率低很多，所以可以使得CPU有时间取做内存刷新的事情。（通过在while代码块中定义个new File()也可达到一样效果）

2. **while代码块中添加Thread.sleep(0)**

   官方文档上是说，Thread.sleep没有任何同步语义，编译器不需要在调用Thread.sleep之前把缓存在寄
   存器中的写刷新到给共享内存、也不需要在Thread.sleep之后重新加载缓存在寄存器中的值。
   编译器可以自由选择读取stop的值一次或者多次，这个是由编译器自己来决定的。
   但是在Mic老师认为：Thread.sleep(0)导致**线程切换**，线程切换会导致缓存失效从而读取到了新的值。

> 如上分析证实了是缓存的原因的导致的，用到的那些解决方法都是间接让缓存同步起到的作用，那么是否有专门让缓存同步的解决方式呢？
>
> 使用**volatile**关键字让缓存同步（保证可见性），直接在变量前加上volatile关键字修饰即可。

------

### volatile分析

- 什么是可见性？

- volatile是如何保证可见性的呢？

#### 可见性

  > 在多线程环境下读和写发生在不同线程的时候，可能会出现读线程不能及时读取到其他线程写入的最新值。

  **为什么会存在可见性问题**

  > CPU > 内存 > IO设备。因为存在处理速度的差异，CPU为了更高效的处理于是增加了下面的优化。
  >
  > - CPU：高速缓存（会导致缓存一致性问题）
  > - OS：进程、线程（CPU时间片来切换，一致性问题）
  > - 编译器优化：更合理的利用CPU的高速缓存（指令重排序）

![10.CPU中缓存结构](.\images\10.CPU中缓存结构.png)

> 一级缓存和二级缓存是各个CPU独占的，三级缓存是共享的。
>
> L1 d：数据缓存；L1 i：指令缓存。
>
> 缓存行：高速缓存、寄存器。



#### 总线锁&缓存锁

> 总线锁简单理解就是，在多CPU下当一个CPU正在操作共享内存时，不允许其他CPU操作共享内存直到释放锁。（实现思路：总线锁把CPU和内存之间的通信锁住,总线上发出LOCK信号，这个信号使得其他处理器无法通过总线来访问到共享内存数据）这种的弊端在于开销非常大。
>
> P6后的CPU架构引入了缓存锁，简单理解就是：让CPU缓存的同一份数据保持一致（实现思路：内存区域若被韩城在处理器的缓存行中，并在lock期间被锁定，那么当它执行锁操作协会到内存时就不在总线上加锁，而是修改内存的内存地址，基于缓存一致性协议来保证操作的原子性？？？？）
>
> 总线锁和缓存锁的选择取决于很多因素，比如CPU是否支持，存在无法缓存的数据时（比较大或者多个缓存行的数据）必然还是使用总线锁。

#### 缓存一致性协议

为了达到数据访问的一致，需要各个处理器在访问缓存是遵循一些协议，常见的有MSI、MOSI、MESI（最常见）。

**MESI表示缓存行的4种状态：**

> 1. M(Modify)表示共享数据只缓存在当前CPU缓存中，并且是被修改的状态，即L缓存的数据和主内存中的数据不一致。
> 2. E(Exclusive)表示缓存的独占状态，数据只缓存在当前CPU缓存中，并且没有被修改。
> 3. S(Share)表示数据可能被多个CPU缓存，并且各个缓存中的大数据和主存数据一致。
> 4. I(Invalid)表示缓存已经失效。

![11.MESI协议-修改值过程](.\images\11.MESI协议-修改值过程.png)

由上图可看到有段过程是阻塞的，为了优化那段阻塞过程提出了Store Bufferes方案。如下

**MESI的一个优化-Store Bufferes：**

> Store Bufferes（每个CPU中都有）是一个写的缓冲，对于上述描述的情况，CPU0可以先把写入的操作先存储到StoreBufferes中，Store Bufferes中的指令再按照缓存一致性协议去发起其他CPU缓存行的失效。而同步来说CPU0可以不用等到Ack，继续往下执行其他指令，直到收到CPU0收到Ack再更新到缓存，再从缓存同步到主内存。
>
> 存在于多个位于不同CPU的缓存行才会用store bufferes优化，如果只存在于一个缓存行就没必要用它优化。

![12.store bufferes优化](.\images\12.store bufferes优化.png)

> 但由于这个过程是异步的会导致CPU层面的指令重排序，但有些地方不能让指令发生重排序于是又弄出个禁止指令重排序的命令，称之为“内存屏障”指令。

#### 内存屏障

用来禁止一些指令重排序（重排序：原本的程序顺序与优化后重排序的顺序不一致）





volatile关键字引入了一个lock指令，触发CPU层面锁的一个操作，可以避免缓存导致的可见性问题。通过内存屏障禁止了指令重排序。

#### 监视器锁规则

一个锁的释放操作一定先与后序线程的加锁操作。

#### start规则

线程中start之前的操作一定先与start操作。