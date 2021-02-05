## 多线程的意义和使用

### 并发

> 并发是指**单位时间内，能够同时处理的请求数**。就像Tomcat默认情况支持150个最大请求数，超过150时就会有响应延迟、丢失连接等问题。
>
> **并发和并行**
>
> - 串行：一次只能取得一个任务并执行这一个任务
>
> - 并行：可以同时通过多线程的方式取得多个任务，并以多进程或多线程的方式同时执行这些任务
> - 并发：并发是一种现象，同时运行多个程序或多个任务需要被处理的现象。（两个或多个事件在同一时间间隔发生）这些任务可能是串行执行的，也可能是并行执行的，与CPU核心数无关，是操作系统进程调度和CPU上下文切换达到的结果
>
> 解决并发的思路通常是将大任务分解成小任务：
>
> - 可以用多线程并行的方式去执行这些小任务达到高效率
> - 用单线程配合多路复用执行这些小任务来达到高效率
>
> 

### 影响服务吞吐的因素

#### 硬件层面

> CPU、内存、磁盘、网络

#### 软件层面

> 从软件层面要做的是最大化利用硬件资源。
>
> 比如让CPU利用率更高（引入多线程并发编程）
>
> 对内存的高效使用（优化算法空间复杂度、及时回收不用的内存）
>
> 提高磁盘效率（磁盘IO，网络通信机制：引入AIO、BIO、NIO）



## 多线程基础

#### 线程的启动

new Thread.**start**(); // 启动线程

run(); // 直接调用方法

> Java是不提供线程的，只是对底层语言进行了封装，调用start方法本质是在调用本地start0()方法启动线程，JVM再根据不同的操作系统创建对应操作系统上的线程再启动线程，轮到该线程执行时（等待操作系统调度算法选中）再从操作系统层面调用JVM中的run方法，JVM中的run方法再调用Java高级语言写的run方法。执行完后再由JVM销毁该线程。

![调用start](.\images\1.调用start.png)

#### 线程的终止

##### **自然终止**

> run()执行结束后会被自然销毁。

##### **非自然终止**

> 由于线程时异步执行的，终止时不受控制，若强制执行则不安全，可能导致数据丢失。所以若要终止线程一般采用中断方式终止。
>
> interrupt()本质是**通过一个线程共享变量来实现线程间的通信**，底层**先设置中断状态**（一个共享变量），**再唤醒处于阻塞状态的线程让其响应中断**（若不唤醒则该线程一直处于阻塞状态就无法响应中断）。
>
> 
>
> **线程中断被设计成异常抛出而不是让sleep返回中断状态？？？**
>
> 推测线程被中断设计为异常的方式抛出，是为了能够事务回滚，如果某些任务没有完成就被中断了就可能导致脏数据。
>
> 之所以那些阻塞状态的操作（如sleep、wait、join……）都需要抛出异常，就是因为有可能有线程来中断它，它就可以在中断异常中对中断做出处理。**是否响应中断由被中断线程决定**。
>
> 线程在InterruptedException中对中断进行处理
>
> ![线程中断](.\images\2.线程中断.png)

**interrupt在C++层面实现代码**

```c++
volatile jint _interrupted; // Thread.isInterrupted state
void os::interrupt(Thread* thread) {
 assert(Thread::current() == thread || Threads_lock->owned_by_self(),
  "possibility of dangling Thread pointer");
 OSThread* osthread = thread->osthread();
 if (!osthread->interrupted()) {
  osthread->set_interrupted(true); //设置一个中断状态
  // More than one thread can get here with the same value of osthread,
  // resulting in multiple notifications. We do, however, want the store
  // to interrupted() to be visible to other threads before we execute
unpark().
  OrderAccess::fence();
  ParkEvent * const slp = thread->_SleepEvent ; //如果是sleep中，唤醒
  if (slp != NULL) slp->unpark() ;
}
 // For JSR166. Unpark even if interrupt status already was set
 if (thread->is_Java_thread())
 ((JavaThread*)thread)->parker()->unpark();
 ParkEvent * ev = thread->_ParkEvent ;
 if (ev != NULL) ev->unpark() ;
}
```



#### 线程状态转化

> 创建线程后为初始状态【**NEW**】，调用start方法启动线程后就绪，当该线程被系统调度选中后变为运行中，就绪和运行中都为运行状态【**RUNNABLE**】，若此时调用了阻塞线程的方法（Object.wait()、Object.join()、LockSupport.park()）后变成等待状态【**WAITING**】，若调用的阻塞线程的方法中指定了时间（Thread.sleep(long)、Object.wait(long)、Object.join(long)、LockSupport.parkNanos()、LockSupport.parkUntil()）就变成超时等待状态【**TIMED_WAITING**】,直到调用了唤醒线程的方法（Object.notify()、Object.notifyAll()、LockSupport.unpark(Thread)）或者阻塞时间到了线程又进入运行状态，当多个线程在竞争同一把锁时没有竞争赢的线程就进入阻塞状态【**BLOCKING**】，若线程执行完了就进入终止状态【**TERMINATED**】。
>
> ![2-1线程状态转换](.\images\2-1线程状态转换.png)
>
> 1、NEW(初始状态)：高级语言层面的线程创建，操作系统并没有真正创建线程，因此也不会获得CPU的执行权，通过调用start()方法进入RUNNABLE状态；
> 2、RUNNABLE(可运行/运行状态）：RUNNABLE状态分可运行和运行两种状态，在java语言中统一叫做RUNNABLE状态，NEW状态的线程通过调用start()方法进入可运行状态，此时操作系统会新建一个线程，当有CPU空闲时，OS会分配CPU执行权，此时进入运行状态。运行时状态也可通过调用yield()方法再次进入可运行状态；
> 3、BLOCKED(阻塞状态）：RUNNABLE状态的线程如果遇到同步锁等待的情况，会进入BLOCKED状态，此时会让出CPU执行权。若同步锁释放，当前线程重新获取CPU执行权并获取锁，此时会从BLOCKED状态重新进入RUNNABLE状态；
> 4、WAITING(无限时等待）：RUNNABLE状态的线程内部调用wait()、sleep()、join()、LockSupport.part()等方法时，会从RUNNABLE状态进入WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 5、TIMED_WAITING(有限时等待）：RUNNABLE状态的线程内部调用wait(long)、sleep(long)、join(long)、LockSupport.partUnit(xx)方法时，会从RUNNABLE状态进入TIMED_WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 6、TERMINATED(终止）：线程运行结束，进入终止状态。也可以通过调用stop()或interrupt()方法进入终止状态，不过stop()方法会直接杀死线程，当前线程持有的锁也不会释放，该方法时不可取的，interrupt()方法仅是通知线程可以终止，具体是否要终止有线程内部自己决定。interrupt()方法会更新线程标识位，并且抛出InterruptedException，线程内部可以通过主动检查线程标志位或捕获InterruptException的方式来获取线程终止的通知。

#### **特性**

> - 异步
> - 并行

#### 应用场景

> 基于这两个特性就有如下应用场景
>
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
> **锁与多线程的关系：**加锁是为了保护资源，与是否多线程没有直接的关系。但是在多线程环境下资源会存在安全问题，所以要保证共享资源的安全就需要对该资源加锁。
>
> **使用：** 修饰方法层面、修饰代码块层面。使用时根据控制的粒度选择。
>
> ```java
>  	/**
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

#### synchronized锁的存储（对象头）

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
   cn.gp.concurrent.SynchronizedDemo object internals:
    OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
         0     4                    (object header)                           08 f8 86 01 (00001000 11111000 10000110 00000001) (25622536)
         4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4                    (object header)                           05 c1 00 20 (00000101 11000001 00000000 00100000) (536920325)
        12     4   java.lang.Object Test.obj                                  (object)
   Instance size: 16 bytes
   Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
   ```

   打印展示数据为大端存储模式下的数据，正常顺序是如下十六进制

   十六进制：0x 00000001 10000110 11111000 00001**000**

   二进制（64位）：看最后三位 000（轻量级锁，通过最后三位查看锁的标记和状态，上图中含义表实际上轻量级锁只通过后面2位判断，无锁和偏向锁主要通过后三位判断）

todo？？？上面的对象头存储所占的空间没搞懂？？？

#### synchronized锁的升级

> 为什么锁会升级？
>
> 因为在有些情况下，锁可能不存在多线程的竞争，最开始就加较轻量级的锁（加偏向锁），后来发现有多线程竞争锁就需要将锁升级（升级为轻量级锁），再后来有严重的竞争就需要将锁升级为重量级的锁。
>
> 总之，锁的升级是出于提升性能考虑，随着锁竞争的增大就需要越“重”的锁。

##### 偏向锁

> 在大多数情况下，锁不仅仅不存在多线程的竞争，而且总是由同一个线程多次获得。在这个背景下就设计了偏向锁。偏向锁顾名思义就是**锁偏向某个线程**。
>
> 当一个线程访问了同步锁的代码块时会在**对象头中存储当前线程的ID**，后续这个线程进入和退出这段加了同步锁的代码块时就**不需要再次加锁和释放锁**，而是**直接比较对象头里面是否存储了指向当前线程的偏向锁**。若相等表示偏向锁是偏向当前线程的就不需要再次尝试获得锁了。引入偏向锁是为了在无多线程竞争的情况下尽量**减少不必要的轻量级锁执行路径**。（偏向锁的目的是**消除数据在无竞争情况下的同步原语**，进一步提高程序的运行性能。）
>
> 偏向锁**默认是开启的，但有4s启动延迟**，另外它只用作优化处理，当优化后遇到线程竞争的情况锁则会自动进行锁的升级。
>
> ```typescript
> // 可以通过下面命令搜索JVM对偏向锁设置的默认值
> // java -XX:+PrintFlagsFinal -version 会打印所有JVM最终参数
> java -XX:+PrintFlagsFinal -version | grep Biased     // Linux下用 grep
> java -XX:+PrintFlagsFinal -version | findstr Biased // Windows下用 findstr
> 
> // 搜索显示出以下内容
>      intx BiasedLockingBulkRebiasThreshold          = 20                                  {product}
>      intx BiasedLockingBulkRevokeThreshold          = 40                                  {product}
>      intx BiasedLockingDecayTime                    = 25000                               {product}
>      intx BiasedLockingStartupDelay                 = 4000                                {product}
>      bool TraceBiasedLocking                        = false                               {product}
>      bool UseBiasedLocking                          = true                                {product}
> java version "1.8.0_202"
> Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
> Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
> 
> // 参数含义解释
> BiasedLockingBulkRebiasThreshold // 批量重偏向。
> BiasedLockingBulkRevokeThreshold // 批量撤销。
> BiasedLockingDecayTime // 腐化的时间。
> BiasedLockingStartupDelay // 启动延迟。
> UseBiasedLocking // 偏向锁开关。
> ```
>
> **可以看到偏向锁默认是开启的，但有4s启动延迟。原因是JVM刚启动时一定有很多线程运行，所以必定会存在多个线程竞争，于是就设置了延迟4s。**
>
> ```typescript
> // 在 VM options中输入以下命令可以设置偏向锁延迟时间为0，在启动程序时就能看到偏向锁作用效果，或者
> -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
> ```
>
> ![6.偏向锁的撤销过程](.\images\6.偏向锁的撤销过程.png)

##### 轻量级锁

> 若**偏向锁被关闭**或**当前偏向锁被其他线程获取**，此时若有线程去抢占同步锁，那么锁会从偏向锁升级到轻量级锁。
>
> ![7.轻量级锁及膨胀过程](.\images\7.轻量级锁及膨胀过程.png)

##### 重量级锁

> - 多个线程竞争一个锁的时候，JVM会阻塞加锁失败的线程，并在模板锁被释放的时候唤醒这些线程。
> - Java中线程的阻塞和唤醒都是由OS完成的。
> - 升级为重量级锁的时候，锁标志状态值变为10，此时Mark Word中**存储的是指向重量级锁的指针**，等待锁的线程都会进入阻塞状态。
>
>   ![8.重量级锁](.\images\8.重量级锁.png)



> **每个对象都会与一个监视器monitor关联**，我们可以把它理解成一把锁，当一个线程想要执行一段被synchronized修饰的同步方法或代码块时该线程先获取到synchronized修饰的对象对应的monitor。
>
> monitorenter表示取获得一个对象监视器，monitorexit表示释放监视器的所有权，使得其他被阻塞的线程可以尝试去获得这个监视器。
>
> **monitor依赖操作系统的MutexLock（互斥锁）来实现**，线程被阻塞后便进入OS**内核调度状态**，这个会导致**OS在用户态和内核态来回切换**，严重影响锁的性能，这就是为什么 Synchronized 效率低的原因。



**乐观锁概念**

> CAS比较预期数据和原始数据是否一致，若一致则修改，不一致则修改失败。整个比较并修改过程是原子操作，所有的原子类底层都是基于cas操作实现。

##### 锁的升级过程

> 无锁升级到偏向锁只会有一次CAS，偏向锁升级到轻量级锁会有多次CAS（1.6前默认最多10次），之所以这儿用多次CAS（自旋锁，自适应自旋）而不是阻塞，是因为线程从获得锁到释放锁过程的时间很短，通过多次CAS抢占锁的性能比阻塞抢占锁的性能更好。但经过10次（这是JDK1.6之前，**1.6之后引入了自适应自旋**：**根据上一次抢占锁时间的长短来决定当前自旋的次数**，上次抢占锁时间到当前抢占到锁的时间段越短，允许的自旋次数就多些）还没抢占到锁就宣告自旋失败，就会升级为重量级锁。
>

#### 线程的通信（wait/notify）

notify/notifyAll，线程的通信都是基于重量级锁来实现的，因为偏向锁和轻量级锁是在自旋中去抢占锁了，只有重量级锁会将抢占失败的锁放在同步队列中。

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

> 以上代码运行后不能停止，看似在线程启动后1s对线程进行了停止操作，但实际上却停止不了。。。猜想一下会是什么原因导致的呢？线程里面停止不了无非就是线程中读到的stop变量一直是false，那么为什么明明改变了stop值读到的值却还是没有变化呢？于是大胆推测是缓存在作祟，毕竟CPU中设计了三级缓存，读到的值与实际值不同很大可能性是缓存不一致导致的。
>

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

> 总线锁简单理解就是，在多CPU下当一个CPU正在操作共享内存时，不允许其他CPU操作共享内存直到释放锁。（实现思路：总线锁**把CPU和内存之间的通信锁住**，总线上发出LOCK信号，这个信号使得其他处理器无法通过总线来操作共享内存数据）这种的弊端在于开销非常大。（总线：CPU与芯片组连接的主干道，负责CPU与外界所有零部件的通信）
>
> 对比总线锁，最好的优化方式是控制锁的粒度，只需保证**被多个CPU缓存的同一份数据是一致的就行**，于是在P6后的CPU架构引入了缓存锁，简单理解就是：**让CPU缓存的同一份数据保持一致**（实现思路：内存区域若被缓存在处理器的缓存行中，并在lock期间被锁定，那么当它执行锁操作回写到内存时就不在总线上加锁，而是修改内部的内存地址，基于缓存一致性协议来保证操作的原子性）
>
> 总线锁和缓存锁的选择取决于很多因素，比如CPU是否支持，存在无法缓存的数据时（比较大或者多个缓存行的数据）必然还是使用总线锁。
>
> PS：缓存行：高速缓存、寄存器都是缓存行（它嵌入CPU核心内的）

#### 缓存一致性协议

为了达到数据访问的一致，需要各个处理器在访问缓存时遵循一些协议，常见的有MSI、MOSI、MESI（最常见）。

**MESI表示缓存行的4种状态：**

> 1. M(Modify)表示共享数据只缓存在当前CPU缓存中，并且是被修改的状态，即L缓存的数据和主内存中的数据不一致。  
> 2. E(Exclusive)表示缓存的独占状态，数据只缓存在当前CPU缓存中，并且没有被修改。
> 3. S(Share)表示数据可能被多个CPU缓存，并且各个缓存中的大数据和主存数据一致。
> 4. I(Invalid)表示缓存已经失效。

![11.MESI协议-修改值过程](.\images\11.MESI协议-修改值过程.png)

由上图可看到有段过程是阻塞的，为了优化那段阻塞过程提出了Store Bufferes方案。如下

**MESI的一个优化-Store Bufferes：**

> Store Bufferes（每个CPU中都有）是一个**写的缓冲**，对于上述描述的情况，CPU0可以先把写入的操作先存储到StoreBufferes中，Store Bufferes中的指令再按照缓存一致性协议去发起其他CPU缓存行的失效。而同步来说CPU0可以不用等到Ack，继续往下执行其他指令，直到收到CPU0收到Ack再更新到缓存，再从缓存同步到主内存。
>
> 存在于多个位于不同CPU的缓存行才会用store bufferes优化，如果只存在于一个缓存行就没必要用它优化。

![12.store bufferes优化](.\images\12.store bufferes优化.png)

> 但由于这个过程是异步的会导致CPU层面的指令重排序，但有些地方不能让指令发生重排序于是又弄出个禁止指令重排序的命令，称之为“内存屏障”指令。

#### 失效队列

> 当一个CPU收到其他CPU对某值的失效请求时，需要将该值放入失效队列再响应其他CPU的失效请求，直到CPU空闲时再处理失效队列。

#### 内存屏障

用来禁止一些指令重排序（重排序：原本的程序顺序与优化后的顺序不一致，优化缓存行的使用）

x86的memory barrier指令包括Ifence（读屏障）sfence（写屏障）mfence（全屏障）

- Store Memory Barrier（写屏障）：**写屏障之前的写操作**不能放到写屏障之后。让CPU把写屏障之前存储在store bufferes中的数据同步到主内存。（写屏障之前的指令结果对屏障后的读写是可见的）
- Load Memory Barrier（读屏障）：**读屏障之后的读操作**不能放到读屏障之前。**配合写屏障使得写屏障之前的操作对读屏障之后的操作可见。**
- Full Memory Barrier（全屏障）：**全屏障前的读写操作均不能放到全屏障后**。确保屏障前的内存读写操作的结果提交到内存后再执行屏障后的读写操作。

![13.内存屏障](.\images\13.内存屏障.png)

#### 硬件层面总结

> 对于缓存一致性问题引入了总线锁和缓存锁来解决，总线锁的原理是**把CPU和共享内存的通信锁住**，当有CPU在操作共享内存时，使得CPU无法通过总线访问到共享内存，这种开销非常大。。。于是引入了缓存锁，原理是**让CPU缓存的同一份数据保存一致**，为了达到这个效果，各个CPU在访问缓存时就必须遵循约定的**缓存协议**，再次就诞生了一系列的缓存协议，其中最常见的是MESI协议。遵循MESI协议的缓存锁处理思路是：当CPU0准备修改一个共享缓存值时，先向其他缓存了该值的CPU发起invalidate请求，CPU0处于阻塞状态，直到其他CPU响应了CPU0发起的invalidate请求，CPU0才能修改缓存值随后同步到主内存。由于这儿有一段阻塞状态，针对这段阻塞状态进行优化提出了**Store Bufferes**，将准备修改的值存入Store Bufferes中，同时向其他缓存了该数据的缓存行发起invalidate请求，然后CPU0不用阻塞，当其他CPU响应了invalidate请求后告诉Store Bufferes，由Store Bufferes将CPU0修改的值同步到主内存。But，由于通过Store Bufferes进行的操作是个异步过程，于是又出现了指令重排序问题（执行顺序与原顺序不同），但有些时候不希望进行重排序，为了解决这个问题于是又引入了**内存屏障**，可以指定禁止一些指令重排序。

## 软件层面

### JMM

JMM设计的有两个点，1.解决不同平台提供的差异化，提供统一的线程安全和缓存一致性模型；2.解决了可见性和有序性问题。

JMM定义了共享内存中多线程程序读写操作的行为规范：在JVM中把共享变量存储到内存以及从内存中取出共享变量的底层实现细节。通过这些规则来规范对内存的读写操作，从而保证指令的正确性。它解决了CPU多级缓存、处理器优化、指令重排序导致的内存访问问题，保证了并发场景下的可见性。

> 需要注意的是：JMM并没有主动限制执行引擎使用处理器的寄存器和高速缓存来提升指令执行速度，也没有主动限制编译器对指令的重排序。也就是说在JMM这个模型上依然存在缓存一致性问题、指令重排序问题。JMM是个抽象模型，它是建立在不同的操作系统和硬件层面上对问题进行了统一的抽象，然后再Java层面提供了一些高级指令，让用户选择在合适的时候去引入这些高级指令来解决可见性问题。

### JMM是如何解决可见性和有序性问题的

JMM基于总线锁和缓存锁提供了volatile、final等关键字让开发者可以在合适的时候增加相应的关键字来禁止高速缓存和指令重排序来解决可见性和有序性问题。

### volatile原理

> 变量不加volatile看字节码文件中就没有ACC_VOLATILE。
>
> 通过javap -v XXX.class查看
>
> ```
> public static volatile boolean stop;
>  descriptor: Z
>  flags: ACC_PUBLIC, ACC_STATIC, ACC_VOLATILE
> ```
>
> 再从JVM的HotSpot层面看代码
>
> ```c++
> // Now store the result 存储值时的操作
> int field_offset = cache->f2_as_index();
>      if (cache->is_volatile()) {
>       if (tos_type == itos) {
>        obj->release_int_field_put(field_offset, STACK_INT(-1));
>      } else if (tos_type == atos) {
>        VERIFY_OOP(STACK_OBJECT(-1));
>        obj->release_obj_field_put(field_offset, STACK_OBJECT(-1));
>        OrderAccess::release_store(&BYTE_MAP_BASE[(uintptr_t)obj >>
> CardTableModRefBS::card_shift], 0);
>      } else if (tos_type == btos) {
>        obj->release_byte_field_put(field_offset, STACK_INT(-1));
>      } else if (tos_type == ltos) {
>        obj->release_long_field_put(field_offset, STACK_LONG(-1));
>      } else if (tos_type == ctos) {
>        obj->release_char_field_put(field_offset, STACK_INT(-1));
>      } else if (tos_type == stos) {
>        obj->release_short_field_put(field_offset, STACK_INT(-1));
>      } else if (tos_type == ftos) {
>        obj->release_float_field_put(field_offset, STACK_FLOAT(-1));
>      } else {
>        obj->release_double_field_put(field_offset, STACK_DOUBLE(-1));
>      }
>       OrderAccess::storeload();
>     }
> ```
>
> 存储值的时候从cache中取（cache是Java中变量在常量池中存储的一个实例）。用is_volatile方法判断变量是否被volatile修饰再对值进行操作，最后加上OrderAccess::storeload指令就是内存屏障指令。里面做了加锁指令，操作寄存器内存等指令。OrderAccess有很多实现类（针对不同OS和CPU架构做了不同实现如linux_x86、windows_x86、solaris_x86等），这些实现类就是保证在各个平台上都能运行的基石。（设置内存屏障还判断OS是单核还是多核，单核不需要设置等）
>
> 简而言之，**volatile底层是通过lock前缀指令调用OS中缓存一致性的处理方法保证可见性的；内存屏障的机制和Happens-Before法则保证有序性**。
>
> 写单例模式中，对变量需要加volatile修饰，还需要double lock check。加volatile修饰的原因是：
>
> new创建对象在底层是三个指令，这三个指令允许重排序，若不加volatile修饰就可能存在对象还是个半成品时其他线程来访问时判断到已经不为null，返回一个半成品对象。



### Happens-Before模型

除了显示引用volatile关键字能够保证可见性以外，在Java中还有其他可见性保证规则。

从JDK1.5开始，引入了一个happens-before的概念，若一个操作的执行结果需要对另一个操作可见，那么这两个操作必须存在happens-before关系，这两个操作可以是同一个线程，也可以是不同线程。

#### 程序顺序规则（as-if-serial语义）

> - 不能改变程序的执行结果（在单线程环境下，执行结果不变）
>
> - 依赖问题，如果两个指令存在依赖关系，是不允许重排序
>
>   ```java
>   void test(){
>     int a=1;
>     int b=1;
>     //int b=1;
>     //int a=1; // 如果只有a和b是允许重排序的
>     int c=a*b;  // 加上c，a和b允许重排序，但c依赖于a和b，c不能重排序
>   }
>   ```

#### 传递性规则

> a happens-before b；b happens-before c；a happens-before c；

#### volatile变量规则

> volatile修饰的变量的写操作一定happens-before后续对于volatile变量的读操作。

volatile关键字引入了一个lock指令，触发CPU层面锁的一个操作，可以避免缓存导致的可见性问题。通过内存屏障禁止了指令重排序。

#### 监视器锁规则

> 一个锁的释放操作一定先与后序线程的加锁操作。
>
> ```java
> int x=10;
> synchronized(this){
>   //后续线程读取到的x的值一定12
>   if(x<12){
>     x=12;
>  }
> }
> x=12;
> ```

#### start规则

> 线程中start之前的操作一定先与start操作。
>
> ```java
> public class StartDemo{
>   int x=0;
>   Thread t1=new Thread(()->{
>     //读取x的值 一定是20
>     if(x==20){
>    }
>  });
>   x=20;
>   t1.start();
> }
> ```

#### Join规则

> ```java
> public class Test{
>   int x=0;
>   Thread t1=new Thread(()->{
>     x=200;
>  });
>   t1.start();
>   t1.join(); //保证结果的可见性。
>   //在此处读取到的x的值一定是200.
> }
> ```

### final关键字提供了内存屏障的规则，可以保证可见性

## 线程基础回顾

### 死锁/活锁

死锁：**一组相互竞争资源的线程**，因**相互等待**导致**“永久”阻塞**的现象。

活锁：任务或执行者没有被阻塞。

### 死锁发生的条件

四个条件同时满足就会产生死锁

> - 互斥：共享资源X和Y只能被一个线程占用；
> - 占有且等待：线程T1获得共享资源X，等待共享资源Y，且不释放共享资源X；
> - 不可抢占：其他线程不能抢占T1已经占用的资源；
> - 循环等待：线程T1等待线程T2占用的资源，线程T2等待线程T1占用的资源。

### 解决死锁

上面条件只需破坏一个就可避免死锁的产生，但互斥没法破坏，因为我们用的锁就是互斥的。

> - 占用且等待：一次性申请所有的资源。
> - 不可抢占：占用部分资源的线程进一步申请其他资源时若申请不到就主动释放它占用的资源。
> - 循环等待：按顺序申请（资源的线性顺序），申请时先申请资源顺序小的，再申请资源序号大的（如按hash码大小作为顺序）。





> 实现：调用wait方法阻塞，Java中线程结束时会调用exit方法，里面有ensure_join()，唤醒阻塞状态下的线程，ensure_join()里面有notify_all()

### ThreadLocal

> 线程隔离机制，保证在多线程环境下对于共享变量的访问的安全性。通过它拿到的值是线程私有的，每个线程拿到的值互不干扰。
>
> 每个线程中都有个ThreadLocalMap，ThreadLocalMap存储结构类似于HashMap，当线程中有多个隔离对象ThreadLocal中时，这些ThreadLocal的值实际上就是放在ThreadLocalMap中存储的Entity内。获取当前线程下某个ThreadLocal的值时，就是先再ThreadLocalMap中找到。
>
> 获取当前线程，根据当前线程获取ThreadLocalMap，若当前线程还没ThreadLocalMap就初始化当前Thread里面的ThreadLocalMap。ThreadLocalMap存的Entry数组，放ThreadLocal中的数据就是存在这Entry（继承弱引用的实体，导致ThreadLocal被回收后key为null）中的。之所以设计成数组是应对一个线程中需要隔离多个数，即使用多个ThreadLocal来隔离。但这儿引入了新问题，多个ThreadLocal对象Entry中的key是否会重复，如果只用hashcode肯定无法避免重复，这儿用hashcode和黄金分割数做完美散列，按它的算法可以很均衡的将key散列在指定长度的数组中。在查找过程根据该算法计算后找对应的下标效率就会高很多。
>
> set时有replaceStaleEntry操作，插入新的值并清理脏的值（ThreadLocal对象为空了，key被回收了为null，就需要清理），它认为当前下标的key为null那么附近的节点也可能有脏的Entry，就需要将其置为null，让gc回收了。另外线性探测还有方面是解决哈希冲突。置为null哪儿是为了解决内存泄露问题。***面试重点

黄金分割数，完美散列。

ThreadLocalMap采用线性探索，因为它里面存储的数据不大，用线性探索就可以满足了，不用链式。

### 面试题

1. **sleep、join、yiled的区别**

> yiled 让出时间片，触发重新调度。
>
> sleep(0) 触发一次切换，阻塞一次后线程重新调度。（功能上和yiled差不多）

2. **Java中能够创建volatile数组吗？**

> 可以创建，volatile对于引用可见，对数组中的元素不具备不可见。
>
> 验证方式
>
> ```java
> private static volatile Object[] objects = new Object[10]; // 查看字节码文件，对应行有lock指令
>     public static void main(String[] args) {
>         objects[0] = 1; // 查看字节码文件，对应行无lock指令
>         objects = new Object[5]; // 查看字节码文件，对应行有lock指令
>
>     }
> ```
>
> volatile 缓存行的填充，与性能有关。每个缓存行有大小，一个缓存行填充两个数据，如果让某个数据的缓存行失效的话就会导致其他缓存其他对象的缓存也失效，就会带来性能问题。所以填充缓存行时要让一个缓存对象填满一个缓存行。

3. **Java中的++操作是线程安全的吗？**

> 不是，线程安全需要同时满足原子性、有序性、可见性。++操作无法满足原子性，它底层由3个指令实现。

4. **线程什么时候会抛出InterruptedException，如何处理中断异常**

> 用t.interrupt()去中断一个处于阻塞状态下（join、sleep、wait）的线程时。
>
> 在异常捕获代码块内处理中断异常。。。

5. **有T1/T2/T3三个线程，如何确保他们的执行顺序**

> join方法让线程间协作

6. **Java内存模型是什么**

> JMM是一个抽象的内存模型。它定义了共享内存中多线程程序读写操作的行为规范：在虚拟机中把共享变量存储到内存以及从内存中取出共享变量的底层实现细节。通过这些规则来规范对内存的读写操作从而保证指令的正确性，它解决了CPU多级缓存、处理器优化、指令重排序导致的内存访问问题，保证了并发场景下的可见性。

7. **什么是线程安全**

> 原子性、有序性、可见性（硬件层面：CPU缓存、指令重排序；软件层面：JMM）





## J.U.C（java.util.concurrent）

### 重入锁

#### 思考锁的实现（设计思维）

**需求**

> 1. 互斥性，共享资源（标记 0无锁 1有锁）
> 2. 没有抢占到锁的怎么办？释放CPU资源->等待->唤醒
> 3. 等待线程怎么存储？数据结构存储等待中的线程，满足FIFO（等待队列）
> 4. 竞争的公平性（是否允许插队）
> 5. 可重入性（标识是否是同一个线程，线程id）

**技术方案**

> 1. volatile state = 0 (无锁)，1(持有锁)，>1(重入锁)
> 2. wait/notify|condition需要唤醒指定线程。[LockSupport.park()；unpark(thread) unsafe类中提供的一个方法(unsafe类是提供的一个可直接操作内存的非安全类)]
> 3. 双向链表存储
> 4. 逻辑层面实现是否允许插队
> 5. 存储当前获得锁的线程id，判断下次抢占锁的是否为同一个线程，如果是重入就让状态累加，要累加是因为锁都是成对出现的，在释放锁时才能正确释放（加了多少就减去多少）。



#### 重入锁的应用案例

```java
public static void inc(){
    lock.lock(); //获得锁(互斥锁) ThreadA 获得了锁
    try {
        //退出线程 中断的过程往下传递.  true
        // sleep/ join/ wait
        //while()
        // ...
        Thread.sleep(1);
        count++;
        decr();// 下一刻重入时分
    } catch (InterruptedException e) {
        e.printStackTrace();
    }finally {
        lock.unlock();//释放锁 ThreadA释放锁  state=1-1=0
    }
}
public static void decr(){
    lock.lock(); //state=2   //ThreadA再次来抢占锁 : 不需要再次抢占锁，而是只增加重入的次数
    try{
        count--;
    }finally {
        lock.unlock(); //state=1
    }
}
```

#### 实现案例

**cas操作：**

> 原子性、有序性、可见性（volatile保证可见性、底层cas操作保证原子性、缓存锁和总线锁）
>
> **注意：**抢占锁时必须用cas操作，**cas操作是原子操作（比较并修改值在OS层面由一条指令完成）**。**用简单的判断值是预期值再写入修改的值只能保证可见性但不是原子操作**，在线程并行情况下就可能出现多个线程同时抢占到一把锁。
>
> cas操作通过java中unsafe类（里面都是native方法）直接通过c++到内存中进行比较和修改值。c++实现cas操作加了volatile保证可见性、也加了缓存锁（MESI）总线锁，cas操作是原子指令，OS层面只有一条指令。

**使用cas操作的时机：**

> 考虑给线程加锁时，要考虑目前是否存在线程安全性，比如在释放锁时对锁占有状态的操作就不需要用cas操作，因为目前是占有锁状态不存在线程安全性。



##### **抢占锁过程：**

```java
// 【1.抢占锁】
private volatile int state;  //互斥资源 0
final void lock() {//不用返回值，若抢占到了锁就返回了，没有抢占到锁就会被阻塞放在阻塞队列中（返不回）
  // 抢占互斥资源（非公平锁的实现，不管当前队列是否有人排队的执行到这儿就尝试去抢占锁，不排队）
  if (compareAndSetState(0, 1)) //乐观锁（true/false）只有一个线程能够进入.（从C++直接操作内存层面保证了可见性问题）
    //能够进入到这个方法 ， 表示无锁状态
    setExclusiveOwnerThread(Thread.currentThread());//保存当前的线程
  else
    acquire(1);
}

// 【2.处理未抢占到锁的线程】
// lock操作如果抢占到了锁就直接返回了，如果没有抢占到锁就执行下面操作
public final void acquire(int arg) {
  if (!tryAcquire(arg) &&
    acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
    selfInterrupt();
    // tryAcquire 尝试获得锁，公平锁和非公平锁有不同的实现
    // addWaiter 将未获得锁的线程加入到阻塞队列（双向链表实现）
    // acquireQueued(); 去抢占锁或者阻塞（队列第一个尝试抢占锁，后面的阻塞(通过LockSupport.park)）
}
// 若没有抢占到锁的线程如果不进行阻塞的话，就会一直在lock处阻塞


// 【3.再次尝试抢占锁，并处理锁的重入】（以下是非公平锁部分）
final boolean nonfairTryAcquire(int acquires) {
  // 获得当前的线程
  final Thread current = Thread.currentThread();
  int c = getState();
  // 之前抢占已经失败过了，先判断下状态再尝试抢占锁，减少抢占失败率
  if (c == 0) { //无锁状态
    if (compareAndSetState(0, acquires)) { //cas
      setExclusiveOwnerThread(current);
      return true;
   }
 }
  // 判断是否重入，是重入则增加state的值
  else if (current == getExclusiveOwnerThread()) {
    int nextc = c + acquires; // 增加state的值
    if (nextc < 0) // overflow
      throw new Error("Maximum lock count exceeded");
    setState(nextc); // 当前以持有锁，所以并不需要通过cas
    return true;
 }
  return false;
}

// 【4.抢占锁失败，到阻塞队列排队】
private Node addWaiter(Node mode) {// 将当前节点加入阻塞队列（双向链表实现）
    Node node = new Node(Thread.currentThread(), mode);// nextWaiter = null
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {// 队列不为空，就直接将当前节点添加到后面
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {// cas操作，此时是未抢占到锁的状态，只有抢占到锁的地方才可以不用cas操作，其他地方都需要
            pred.next = node;
            return node;
        }
    }
    enq(node);// 队列为空时，初始化头节点并将当前节点加入队列
    return node;
}
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize，让head和tail都指向一个没有线程的Node
            if (compareAndSetHead(new Node()))// 这儿初始化头节点，head除了setHead还有就是这儿从底层改了head
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) {// 将tail设置为当前node
                t.next = node;
                return t;
            }
        }
    }
}


// 【5.抢占或阻塞锁，自旋】
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) && // 将取消等待的节点移除阻塞队列
                parkAndCheckInterrupt())// 阻塞队列中线程就阻塞在这儿，若某线程被中断也就是从这个方法往上返回
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

**阻塞队列变化情况：**

![线程添加到阻塞队列过程](.\images\14.线程添加到阻塞队列过程.png)



##### **释放锁过程：**

> 释放锁之后从阻塞队列中取出**头结点的下一个线程**将其唤醒（unpark），让头结点的下一个节点线程自旋（阻塞在for循环中，唤醒后继续for循环，改变部分状态）自旋过程找当前上个节点（这儿找到头节点）让上个节点抢占锁，将头节点的下一个节点设置为头结点。

```java
// 释放锁
public void unlock() {
    sync.release(1);
}

public final boolean release(int arg) {
    if (tryRelease(arg)) {// 释放锁（更变锁占有状态、当前独占线程置为空）
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h); // 唤醒阻塞队列中头节点的下个线程
        return true;
    }
    return false;
}

// 唤醒节点、移除取消状态的节点
private void unparkSuccessor(Node node) {
  /*
  * If status is negative (i.e., possibly needing signal) try
  * to clear in anticipation of signalling.  It is OK if this
  * fails or if status is changed by waiting thread.
  */
  int ws = node.waitStatus;
  if (ws < 0)
    compareAndSetWaitStatus(node, ws, 0);

  /*
  * Thread to unpark is held in successor, which is normally
  * just the next node.  But if cancelled or apparently null,
  * traverse backwards from tail to find the actual
  * non-cancelled successor.
  */
  Node s = node.next;
  if (s == null || s.waitStatus > 0) {
    s = null;
    // 移除取消状态的节点，从后往前遍历(必须双链表)
    for (Node t = tail; t != null && t != node; t = t.prev)
      if (t.waitStatus <= 0)
        s = t;
  }
  // 唤醒的是s节点的线程，头节点的下个节点
  if (s != null)
    LockSupport.unpark(s.thread);
}

// 锁自旋
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor(); // 头节点的下个节点线程醒了，自旋时p=头节点
            if (p == head && tryAcquire(arg)) { // 当前唤醒的线程是node节点线程
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) && // 将取消等待的节点移除阻塞队列
                parkAndCheckInterrupt())// 阻塞队列中线程就阻塞在这儿，若某线程被中断也就是从这个方法往上返回
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}

// 抢占锁(唤醒过程)
protected final boolean tryAcquire(int acquires) {
  final Thread current = Thread.currentThread(); // 当前线程是被唤醒的node节点所在线程
  int c = getState();
  if (c == 0) {
    if (!hasQueuedPredecessors() &&
        compareAndSetState(0, acquires)) {
      setExclusiveOwnerThread(current);
      return true;
    }
  }
  else if (current == getExclusiveOwnerThread()) {
    int nextc = c + acquires;
    if (nextc < 0)
      throw new Error("Maximum lock count exceeded");
    setState(nextc);
    return true;
  }
  return false;
}
```

**阻塞队列变化情况：**

![释放锁时线程自旋过程](.\images\15.释放锁时线程自旋过程.png)

**抢占释放锁整体过程：**

![抢占释放锁整体过程](.\images\16.抢占释放锁整体过程.png)

##### **锁的公平性：**

> 非公平锁在抢占锁时，执行到 lock() 就去抢占锁，没有抢占到锁就去阻塞队列排队。而公平锁执行到 lock() 不会直接抢占锁，会判断阻塞队列里是否有值，队列为空才去抢占锁，队列不为空就在阻塞队列排队。
>
> 公平锁&非公共锁选择
> 	公平锁
> 		优点：等待锁的线程不会出现饿死
> 		缺点：整体吞吐效率比非公平锁低等待队列中除第一个线程外，其他线程都会阻塞，CPU唤醒阻塞线程的开销比非公平锁大
> 	非公平锁（默认）
> 		优点：整体的吞吐效率高，线程有几率不阻塞直接获得锁，这时CPU不用唤醒其他线程
> 		缺点：可能出现饿死的线程

```java
// 在实例化ReentrantLock时传参决定是什么锁，默认非公平锁
public ReentrantLock() {
    sync = new NonfairSync();
}
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}

// 主要差异如下
// 【非公平锁】
final void lock() {
    if (compareAndSetState(0, 1)) // 执行到lock()就尝试抢占锁
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
final boolean nonfairTryAcquire(int acquires) {
  // 获得当前的线程
  final Thread current = Thread.currentThread();
  int c = getState();
  // 再次尝试抢占锁
  if (c == 0) { //无锁状态
    if (compareAndSetState(0, acquires)) { //cas
      setExclusiveOwnerThread(current);
      return true;
   }
 }
  // 判断是否重入，是重入则增加state的值
  else if (current == getExclusiveOwnerThread()) {
    int nextc = c + acquires; // 增加state的值
    if (nextc < 0) // overflow
      throw new Error("Maximum lock count exceeded");
    setState(nextc); // 当前以持有锁，所以并不需要通过cas
    return true;
 }
  return false;
}

// 【公平锁】
final void lock() {
    acquire(1); // 执行到lock()不忙抢占锁，先看阻塞队列看看，阻塞队列为空再抢占锁
}
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() && // 阻塞队列没有前置（空队列）再抢占锁
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

##### 中断处理：

**中断某线程**

```java
lock.lock(); // 获得锁(互斥锁)
try {
    // 当前线程抢占到锁时，执行到这儿发现被中断则退出线程
    // 中断的过程往下传递.  true
    // sleep/ join/ wait
    while (Thread.currentThread().isInterrupted()){
        // todo
    }
} catch (InterruptedException e) {
    e.printStackTrace();
}finally {
    lock.unlock();//释放锁
}
```

**中断传递与处理过程**

> **当被阻塞线程抢占到锁时才能处理中断**
>
> 抢占锁的过程中没有处理中断信息，在**unpark()下面返回的中断状态**，如果线程在阻塞过程中被中断了就从这儿返回信息，将中断往上传递，传递到acquire()中让selfInterrupt()处理中断，直到被中断的线程抢占到锁的时候再处理中断。之所以要往上传递，因为在锁自旋过程让**被阻塞线程获得锁时才处理中断状态**。（白话：休假过程接到较紧急任务，但我不忙处理，等休假回来再处理，因为在休假过程不能处理）

```java
	final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;// 当前线程获得锁时，返回中断状态
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())// 线程被阻塞到这里面，如果被中断了就会返回true，进而更改下面的interrupted，继续执行直到当前线程获得锁时将中断状态返回
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        // 中断状态返回后，这儿进行的中断操作Thread.currentThread().interrupt();
        selfInterrupt();
}
```

### 常见并发工具

#### Condition

**作用：**多线程协调通信工具类。让某些线程一起等待某个条件（condition），当条件满足时线程才被唤醒。

##### 基本使用

**重要方法：**

> - await：将当前线程挂起阻塞（会释放锁）
> - signal：唤醒阻塞的线程

###### **使用案例：生产者消费者模型**

> 用Condition和synchronized关键字都可以实现。
>
> 生产者先生产，只要生产了一个消息放在队列中就可通知消费者（signal）（但消费者啥时候能抢占到锁就看它造化了），直到到队列满了生产者就停止生产（await，阻塞生产者线程、释放锁并间接唤醒消费者线程）。消费者被唤醒抢占到锁后去队列中取，只要队列可以能继续放下消息就可通知生产者（signal），直到队列中没有消息了就停止消费（await，阻塞消费者者线程、释放锁并间接唤醒生产者线程）；生产者线程被唤醒后继续生产。
>
> 实现步骤如下：
>
> 0. 抢占锁，准备生产;
> 1. 判断是否达到最大生产数，若达到则阻塞并释放锁
> 2. 生产消息（往队列中添加消息）
> 3. 通知消费者（只要有消息了就去通知，消费者啥时候消费看它啥时候能抢占到锁）
> 4. 释放锁
>
> 下面是使用condition实现的案例：

**生产者**

```java
public class Producer implements Runnable {

    /**
     * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过来；
     * 或者定义成类变量，生产者消费者通过类取到该变量。
     */
    Deque<String> msg;
    int maxSize ;
    Lock lock;
    Condition condition;

    public Producer(Deque queue, int maxSize, Lock lock, Condition condition){
        this.msg = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            i++;
            // 0.抢占锁，准备生产（注意位置是在while(true)里面）
            lock.lock();
            try{
                // 1.达到最大生产数，阻塞并释放锁
                while (msg.size() == maxSize){
                    System.out.println("队列【满】了，等待消费中……");
                    // 释放锁唤醒消费者线程，并将当前线程加入condition队列(阻塞)
                    /*
                    消费者线程被阻塞后释放锁唤醒当前生产者线程，
                    之后消费者线程被加入condition队列(阻塞)
                     */
                    /*
                    还可能是通过consumer中的signal唤醒的，放在同步队列中
                     */
                    condition.await();
                }
                Thread.sleep(1000);
                // 2.生产消息
                msg.add("生产的消息"+i);
                System.out.println("【生产】："+msg.getLast());
                // 3.通知消费者（只要有消息了就去通知，消费者啥时候消费看它啥时候能抢占到锁）
                condition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                // 4.释放锁
                lock.unlock();
            }

        }
    }
}
```

**消费者**

```java
public class Consumer implements Runnable {

    /**
     * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过来；
     * 或者定义成类变量，生产者消费者通过类取到该变量。
     */
    Deque<String> msg;
    int maxSize ;
    Lock lock;
    Condition condition;

    public Consumer(Deque queue, int maxSize, Lock lock, Condition condition){
        this.msg = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }


    @Override
    public void run() {
        while (true){
            // 0.抢占锁，准备消费（注意位置是在while(true)里面）
            lock.lock();
            try{
                // 1.队列空了，阻塞等待
                while (msg.isEmpty()){
                    System.out.println("队列【空】了，等待生产中……");
                    // 阻塞线程并释放锁
                    condition.await();
                }
                Thread.sleep(1000);
                // 2.正常消费消息
                System.out.println("【消费】："+ msg.remove());
                // 3.唤醒生产者
                condition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                // 4.释放锁
                lock.unlock();
            }
        }
    }
}
```

**应用测试类**

```java
public class App {
    public static void main(String[] args) {
        /**
         * 以下几个参数为生产者消费者共用的参数，可在使用时定义通过构造参数传过去；
         * 或者定义成类变量，生产者消费者通过类取到该变量。
         */
        Deque<String> queue = new LinkedList<>();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int maxSize = 5;

        Producer producer = new Producer(queue, maxSize,lock,condition);
        Consumer consumer = new Consumer(queue, maxSize,lock,condition);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
```

> 使用synchronized实现时就用wait/notify来实现通知和唤醒，wait也是需要释放锁的，不释放锁则没有办法让其他线程抢占锁，就会形成死锁。
>



###### 设计思维

**需求**

> 1. 调用await()后释放锁，并进入等待队列。
> 2. 调用signal()后唤醒线程。

**设计**

> 可以和上面重入锁差不多的设计。
>
> await():用unlock释放锁的方式释放锁，释放锁后将线程加入等待队列中，链表做阻塞队列存储等待的线程。
>
> signal():到等待队列中唤醒线程，让其去AQS中抢占锁，抢占到了锁才能继续执行。




##### 源码分析

###### await

> 调用Condition的await()（或await开头的方法）会使当前线程**释放锁并进入等待队列且线程状态变为等待状态**。当从await()返回时当前线程一定获取了Condition相关的锁。

```java

public final void await() throws InterruptedException {
  if (Thread.interrupted())
    throw new InterruptedException();
  Node node = addConditionWaiter(); // 将当前线程添加到condition队列中
  int savedState = fullyRelease(node); // 完全释放锁（若是重入锁 status>1需要完全释放）
  int interruptMode = 0;
  while (!isOnSyncQueue(node)) { // 判断是否在AQS同步队列中，通过状态检查和遍历的方式判断（作用：将当前线程阻塞，如果在AQS同步队列中说明已经阻塞了就不用再阻塞，没在同步队列中就需要将其阻塞）
    // 不在阻塞队列中，则将其阻塞
    LockSupport.park(this);
    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
      break;
  }
  // acquireQueued阻塞+自旋
  if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
    interruptMode = REINTERRUPT;
  if (node.nextWaiter != null) // clean up if cancelled
    unlinkCancelledWaiters();
  if (interruptMode != 0) // 处理中断响应方式
    reportInterruptAfterWait(interruptMode);
}

// 判断当前线程是否在AQS同步队列中
final boolean isOnSyncQueue(Node node) {
  // 两种快捷验证方式，节点状态为在condition队列的状态或没有前置节点(同步队列的节点都有前置节点)此时说明肯定不在同步队列中；若当前节点存在后置节点就说明肯定在同步队列中。
  if (node.waitStatus == Node.CONDITION || node.prev == null)
    return false;
  if (node.next != null) // If has successor, it must be on queue
    return true;
  /*
         * node.prev can be non-null, but not yet on queue because
         * the CAS to place it on queue can fail. So we have to
         * traverse from tail to make sure it actually made it.  It
         * will always be near the tail in calls to this method, and
         * unless the CAS failed (which is unlikely), it will be
         * there, so we hardly ever traverse much.
         */
  // 前面两种快捷验证方式没法验证则通过效率低些的遍历验证
  return findNodeFromTail(node);
}




```



###### signal

```java


public final void signal() {
  if (!isHeldExclusively())
    throw new IllegalMonitorStateException();
  Node first = firstWaiter;
  if (first != null) // 等待队列中有线程就唤醒
    doSignal(first);
}

// 
private void doSignal(Node first) {
  do {
    if ( (firstWaiter = first.nextWaiter) == null) // condition队列为空时将lastWaiter置空
      lastWaiter = null;
    first.nextWaiter = null; // 将当前节点与下个节点之间的断开，将当前GC
  } while (!transferForSignal(first) &&  // 将first节点中的线程传输到同步队列，等待抢占锁（被唤醒）
           (first = firstWaiter) != null);
}


final boolean transferForSignal(Node node) {
  /*
  * If cannot change waitStatus, the node has been cancelled.
  */
  if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
    return false;

  /*
  * Splice onto queue and try to set waitStatus of predecessor to
  * indicate that thread is (probably) waiting. If cancelled or
  * attempt to set waitStatus fails, wake up to resync (in which
  * case the waitStatus can be transiently and harmlessly wrong).
  */
  Node p = enq(node); // 传输到同步队列，返回的node的上个节点
  int ws = p.waitStatus;
  // 【注意：性能优化的点】
  // 若node前驱节点线程是取消状态，取消状态的线程在同步队列中需要移除掉，而在调用unlock时是先移除取消状态的节点再唤醒头节点的下个线程，移除过程需要遍历(耗时)，这儿直接唤醒node的线程，如果node位于头节点的下个节点，那么node线程就可以直接抢占到锁；如果不是头节点的下个线程就继续阻塞。
  if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
    LockSupport.unpark(node.thread);
  return true;
}
```

##### 图解

![17.condition过程](.\images\17.condition过程.png)



##### 应用场景

> 该工具是并发包中实现其他并发工具的基础，比如阻塞队列的实现就需要基于condition来进行条件控制。

#### **阻塞队列**

> 阻塞队列（XXBlockingQueue、XXBlockingDeque）：当生队列中消息长度大于设定的最大长度时就阻塞生产者，当队列长度为零时就阻塞消费者。

**主要方法**

> - add()->如果队列满了，则报错
> - offer()->会返回添加元素成功的状态
> - put() ->阻塞式的添加数据

##### 思考实现（设计思维）

> 实例化阻塞队列时指定队列固定的容量，并实例化一个锁以及初始化线程条件控制的condition，分别是当线程个数达到最大容量时就不能继续添加了，让当前线程等待并通知从队列取数据的线程。
>

> 官方实现中，用到了两个条件控制，一个控制put的线程们，满了就让它等；一个控制take的线程们，空了就让他们等。

##### 源码分析

```java
// =================放元素====================
public void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        while (count == items.length)
            notFull.await();
        enqueue(e);
    } finally {
        lock.unlock();
    }
}
private void enqueue(E x) {
    // assert lock.getHoldCount() == 1;
    // assert items[putIndex] == null;
    final Object[] items = this.items;
    items[putIndex] = x;
    if (++putIndex == items.length)
        putIndex = 0;
    count++;
    notEmpty.signal();
}


// =================取元素====================
public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        while (count == 0)// 队列中没有元素时就让当前线程等待（阻塞）
            notEmpty.await();
        return dequeue();// 将队列中的元素取出
    } finally {
        lock.unlock();
    }
}

private E dequeue() {
    // assert lock.getHoldCount() == 1;
    // assert items[takeIndex] != null;
    final Object[] items = this.items;
    @SuppressWarnings("unchecked")
    E x = (E) items[takeIndex];// 取元素
    items[takeIndex] = null;
    if (++takeIndex == items.length)
        takeIndex = 0;
    count--;
    if (itrs != null)
        itrs.elementDequeued();
    notFull.signal();// 通知放元素的线程
    return x;
}
```



#### 构建异步化的责任链

技术点：Thread(异步)、volatile(线程通信可见性)、BlockedQueued(存放！多个消息)、责任链模式







#### CountDownLatch

> 用来控制线程等待，可以让某个线程等待直到倒计数结束再开始执行。
> 类比：火箭发射前等各项设备检查无故障后才允许点火。

> 猜想通过state进行递减控制

初始化时将数字设置到state中。

共享锁：唤醒线程时将同步队列中的全部唤醒，使用传递方式唤醒。

##### await()

```java
public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}

 public final void acquireSharedInterruptibly(int arg)
     throws InterruptedException {
     if (Thread.interrupted())
         throw new InterruptedException();
     if (tryAcquireShared(arg) < 0)
         doAcquireSharedInterruptibly(arg);
 }


private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);//设置头节点并且传递
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt()) // 会阻塞在这儿，唤醒线程时也就是从这儿继续执行
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}


// 添加到等待队列
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;// 这儿代码块非原子操作，若执行到这儿前被其他中断了，就会导致设置了tail但next为空
            return node;
        }
    }
    enq(node);
    return node;
}

// 传递唤醒
private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; // Record old head for check below
        setHead(node);
        /*
         * Try to signal next queued node if:
         *   Propagation was indicated by caller,
         *     or was recorded (as h.waitStatus either before
         *     or after setHead) by a previous operation
         *     (note: this uses sign-check of waitStatus because
         *      PROPAGATE status may transition to SIGNAL.)
         * and
         *   The next node is waiting in shared mode,
         *     or we don't know, because it appears null
         *
         * The conservatism in both of these checks may cause
         * unnecessary wake-ups, but only when there are multiple
         * racing acquires/releases, so most need signals now or soon
         * anyway.
         */
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
            (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            // s==null的情况是在addWaiter时建立了prev以及设置了tail后给next赋值前被其他线程中断了就会出现这个next==null
            if (s == null || s.isShared())
                doReleaseShared();// 传递
        }
    }

```





##### countDown()

唤醒锁，将state递减

```java
public void countDown() {
    sync.releaseShared(1);
}

public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}


// 共享锁是可以唤醒所有的线程，而不是针对某一个。可以由多个线程同时抢占到锁
protected int tryAcquireShared(int acquires) {
    for (;;) { // 循环是因为可能有多个线程同时进行cas修改，但每次都只有一个修改成功，这儿让修改失败的多次尝试，直到state为0
        if (hasQueuedPredecessors())
            return -1;
        int available = getState();
        int remaining = available - acquires;
        if (remaining < 0 ||
            compareAndSetState(available, remaining))
            return remaining;
    }
}


private void doReleaseShared() {
        /*
         * Ensure that a release propagates, even if there are other
         * in-progress acquires/releases.  This proceeds in the usual
         * way of trying to unparkSuccessor of head if it needs
         * signal. But if it does not, status is set to PROPAGATE to
         * ensure that upon release, propagation continues.
         * Additionally, we must loop in case a new node is added
         * while we are doing this. Also, unlike other uses of
         * unparkSuccessor, we need to know if CAS to reset status
         * fails, if so rechecking.
         */
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                unparkSuccessor(h);// 唤醒时存在锁的传递，和之前其他地方唤醒线程不一样，主要逻辑在await阻塞中实现的传递唤醒，传递唤醒时会多次进入此方法
            }
            else if (ws == 0 &&
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        }
        if (h == head)                   // loop if head changed
            break;
    }
}
```



##### Semaphore

> 流量控制，设定n个令牌数量，只有拿到令牌才能继续往下执行，没拿到就阻塞。



##### Automic原子操作（安全性）

> 底层使用cas实现



### ConcurrentHashMap源码分析

hash函数：MD5、SHA

> 源码分为5个阶段解读

#### JDk1.7与1.8变化



#### put()第一阶段（初始化）

> keys:
>
> 1. initTable:防止多个线程初始化，用cas保证线程安全性
> 2. tabAt:等价于单线程下的tabl[i]，但要保证可见性，用getObjectVolatile直接取内存中取值，虽然**table数组本身加了volatile修饰，但这个只针对修改数组引用可见**，而不是数组中的元素。
> 3. casTabAt:通过cas操作将put进来的值封装在Node中放在tab对应位置
>
> **sizeCtl**是数组初始化或扩容是用的控制标识位
>
> - 负数：正在进行初始化或扩容操作；
> - -1：正在初始化、-N：有N-1个线程正在进行扩容操作；
> - 0：数组还未初始化；
> - 正数：初始化或下一次扩容的大小

```java
// putVal 初始化部分
if (key == null || value == null) throw new NullPointerException();
int hash = spread(key.hashCode()); // 计算hash值
int binCount = 0;
for (Node<K,V>[] tab = table;;) {
    Node<K,V> f; int n, i, fh;
    if (tab == null || (n = tab.length) == 0)
        tab = initTable(); // 初始化table
    else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {// 计算位置下标，当前下标位置无节点时，通过cas操作给该下标位置设置上当前键值对的节点（此时没占有锁，需要cas操作）
        if (casTabAt(tab, i, null,
                     new Node<K,V>(hash, key, value, null)))
            break;                   // no lock when adding to empty bin
    }
//    ……
}

// initTable
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    while ((tab = table) == null || tab.length == 0) {// 可能多个线程同时初始化，用cas操作保证线程安全性
        if ((sc = sizeCtl) < 0)// 表示已经有线程已经初始化了或者正在初始化（下面竞争时通过cas操作将该值设置为-1），则不去竞争了，减少线程竞争带来的开销。
            // sizeCtl是数组初始化或扩容是用的控制标识位，负数代表正在进行初始化或扩容操作。-1：正在初始化、-N：有N-1个线程正在进行扩容操作、0：数组还未初始化、正数：初始化或下一次扩容的大小
            Thread.yield(); // lost initialization race; just spin
        else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {// cas操作，只会有一个线程进入下面逻辑，就能够保证初始化部分只有一个线程能操作，从而保证线程安全性	
            try {
                if ((tab = table) == null || tab.length == 0) {
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    @SuppressWarnings("unchecked")
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    table = tab = nt;
                    sc = n - (n >>> 2); // 计算下次扩容的阈值大小，实际上就是当前容量的0.75倍，这儿用(n - n无符号右移两位)计算
                }
            } finally {
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}
```





```java
// putVal完整
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    int hash = spread(key.hashCode()); // 计算hash值
    int binCount = 0; // 记录链表长度
    for (Node<K,V>[] tab = table;;) { // 自旋，线程竞争时不断自旋
        Node<K,V> f; int n, i, fh;
        if (tab == null || (n = tab.length) == 0)
            tab = initTable(); // 初始化数组
        // 通过hash值取到对应数组下标，tab在自旋过程读取table(volatile修饰)
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            // 该下标节点为空，则cas操作插入新node，若cas失败则说明存在竞争，下次循环自旋
            if (casTabAt(tab, i, null,
                         new Node<K,V>(hash, key, value, null)))
                break;                   // no lock when adding to empty bin
        }
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        else {
            V oldVal = null;
            synchronized (f) { // 给当前数组下标对应的node节点加锁
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) {
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {// 覆盖旧值
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {// 链式散列，hash冲突
                                pred.next = new Node<K,V>(hash, key,
                                                          value, null);
                                break;
                            }
                        }
                    }
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                              value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    addCount(1L, binCount);
    return null;
}
```





##### 附

> 源码中经常有将全局变量赋值给局部变量，再操作局部变量，操作完后再赋值给全局变量，这是一种性能优化的方式，从JVM层面理解。操作本方法中的变量比操作全局变量性能高





#### put()第二阶段（元素个数统计和更新）

> 思考：更新元素，是否是线程并发的更新，并发更新一个共享变量的值，如何保证性能高、安全？

##### addCount()添加元素个数

> 常规化是用乐观锁不断的自旋修改，但是在线程竞争特别激烈的情况，失败率很高，开销就非常大，所以引入了通过CounterCell[]中取value，再去cas修改，相当于增加了乐观锁的个数，每个counterCell中存储其他线程添加了元素的计数，最后**baseCount + (counterCells中每个CounterCell的value之和) = sum**。（非常重要的分片思想：线程竞争太大导致一个乐观锁不够用(失败率高，开销大)，于是引入CounterCell多加几个乐观锁，最后统计时把原本的数量和其他乐观锁中统计的数量相加即可）
>
> keys：
>
> 1. 引入CounterCell统计数量，以解决竞争太激烈情况下用一个baseCount效率低问题
> 2. fullAddCount:CounterCell数组的初始化和扩容
> 3. sumCount:统计元素总个数，累加所有counterCells中的value和baseCount
> 4. transfer:table扩容

```java
private transient volatile long baseCount;// 没有竞争的情况下，通过cas操作更新元素个数
private transient volatile CounterCell[] counterCells;// 竞争情况下，存储元素个数
// baseCount + (遍历counterCells每个CounterCell中的value) = sum
private final void addCount(long x, int check) {
    CounterCell[] as; long b, s;
    // counterCells不为空、在baseCount上cas修改失败(此时counterCells为空)均表示竞争较激烈，就记录在CounterCell中
    if ((as = counterCells) != null ||
        !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
        CounterCell a; long v; int m;
        boolean uncontended = true;// 是否冲突标识，默认没有冲突
        /*
        1.计数表为空，直接调用fullAddCount
        2.从计数表中随机取出一个数组的位置为空，直接调用fullAddCount
        3.通过cas修改CounterCell随机位置的值，若修改失败则调用fullAddCount
        Random在线程并发时可能出现相同随机数，用ThreadLocalRandom.getProbe()适用于并发情况生成随机数，且效率比Random高
        */
        if (as == null || (m = as.length - 1) < 0 ||
            (a = as[ThreadLocalRandom.getProbe() & m]) == null || // 随机选择一个CounterCell
            !(uncontended =
              U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
            fullAddCount(x, uncontended);
            return;
        }
        if (check <= 1)// 链表长度小于1，不需扩容
            return;
        s = sumCount();// 统计元素个数
    }
    if (check >= 0) {
        Node<K,V>[] tab, nt; int n, sc;
        while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
               (n = tab.length) < MAXIMUM_CAPACITY) {
            int rs = resizeStamp(n);
            if (sc < 0) {
                if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                    sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                    transferIndex <= 0)
                    break;
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                    transfer(tab, nt);
            }
            else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                         (rs << RESIZE_STAMP_SHIFT) + 2))
                transfer(tab, null);// table扩容
            s = sumCount();
        }
    }
}

// 累加baseCount和counterCells中的value值
final long sumCount() {
    CounterCell[] as = counterCells; CounterCell a;
    long sum = baseCount;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}
```

###### fullAddCount（CounterCell数组的初始化和扩容）

> 初始化CounterCell，CounterCell扩容等操作。
>
> keys：
>
> 1. 与CounterCell相关操作需要用到取随机数，尽可能使之在多线程环境下较均匀落在CounterCell数组中
> 2. 初始化大小为2，扩容时按2倍进行扩容
> 3. 在初始化和扩容阶段需要cas操作，并设置一些标识位

```java
private final void fullAddCount(long x, boolean wasUncontended) {
    int h;
    // 获取当前线程的probe(随机数)的值，若值为0则初始化当前线程的probe的值
    if ((h = ThreadLocalRandom.getProbe()) == 0) {
        ThreadLocalRandom.localInit();      // force initialization
        h = ThreadLocalRandom.getProbe();
        wasUncontended = true; // 重新生成了probe，未冲突标志位设置为true
    }
    boolean collide = false;                // True if last slot nonempty
    for (;;) {
        CounterCell[] as; CounterCell a; int n; long v;
        // 
        if ((as = counterCells) != null && (n = as.length) > 0) {
            if ((a = as[(n - 1) & h]) == null) {
                // cellsBusy=0表示counterCells不在初始化或扩容状态下
                if (cellsBusy == 0) {            // Try to attach new Cell
                    CounterCell r = new CounterCell(x); // Optimistic create
                    if (cellsBusy == 0 &&
                        //通过cas设置cellBusy标识，防止其他线程来对counterCells并发处理
                        U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                        boolean created = false;
                        try {               // Recheck under lock
                            CounterCell[] rs; int m, j;
                            if ((rs = counterCells) != null &&
                                (m = rs.length) > 0 &&
                                rs[j = (m - 1) & h] == null) {
                                rs[j] = r;
                                created = true;
                            }
                        } finally {// 恢复标志位
                            cellsBusy = 0;
                        }
                        if (created)// 创建成功，退出循环
                            break;
                        // 说明指定cells下标位置的数据不为空，则进行下次循环(自旋)
                        continue;           // Slot is now non-empty
                    }
                }
                collide = false;
            }
            // 说明在addCount()中cas失败了，并且获得probe的值不为空
            else if (!wasUncontended)       // CAS already known to fail
                wasUncontended = true;      // Continue after rehash
            // 由于指定下班位置的cell值不为空，则直接通过cas进行原子累加，若成功则直接退出
            else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))
                break;
            // 若已有其他线程建立了新的counterCells或counterCells大于CPU核心数（很巧妙，线程的并发数不会超过CPU核心数）
            else if (counterCells != as || n >= NCPU)
                // 设置当前线程的循环失败不进行扩容
                collide = false;            // At max size or stale
            else if (!collide)
                collide = true;
            // 进入这个步骤说明CounterCell数组容量不够，线程竞争较大，所以先设置一个标识表示正在扩容
            else if (cellsBusy == 0 &&
                     U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                try {
                    if (counterCells == as) {// Expand table unless stale
                        // 【扩容1倍】
                        CounterCell[] rs = new CounterCell[n << 1];
                        for (int i = 0; i < n; ++i)
                            rs[i] = as[i];
                        counterCells = rs;
                    }
                } finally {
                    cellsBusy = 0;// 恢复标识
                }
                collide = false;
                continue;                   // Retry with expanded table
            }
            h = ThreadLocalRandom.advanceProbe(h);// 更新随机数的值
        }
        // cellsBusy == 0表示没有初始化，cas更新它标识正在初始化
        else if (cellsBusy == 0 && counterCells == as &&
                 U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
            boolean init = false;
            try {                           // Initialize table
                if (counterCells == as) {
                    CounterCell[] rs = new CounterCell[2];// 【初始化容量为2】
                    rs[h & 1] = new CounterCell(x);// 将元素的个数放在指定的下标位置
                    counterCells = rs;
                    init = true; // 设置初始化完成标识
                }
            } finally {
                cellsBusy = 0; // 恢复标识
            }
            if (init)
                break;
        }
        // 竞争激烈，其他线程占据cell数组，直接累加在baseCount中（优化方式）
        else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))
            break;                          // Fall back on using base
    }
}
```

##### transfer

> 扩容核心在于数据转移，多线程环境下可能有线程正在转移元素，其他线程又触发了扩容，这儿没有直接加互斥锁而是采用cas实现无锁的并发同步策略，最精华的地方在于可以用多线程来进行协同扩容。

**实现思路：**

> 把Node数组作为线程间共享的任务队列，通过维护一个指针来划分每个线程锁负责的区间（每个区间有若干个bucket），每个线程通过对区间内逆向遍历实现扩容。已经完成迁移的bucket被替换成ForwardingNode节点，标记当前bucket已完成迁移。



> table扩容，当更新后的键值对总数baseCount >= 阈值sizeCtl时，进行rehash。注意这儿有两种逻辑：
>
> 1. 当前没有扩容，直接触发扩容
> 2. 当前正在扩容，当前线程则协助扩容
>
> keys：
>
> 	1. resizeStamp:生成唯一的扩容戳
>
>  	2. transfer:扩容

```java
// addCount()中后半段扩容相关代码
if (check >= 0) {
    Node<K,V>[] tab, nt; int n, sc;
    // s:当前集合大小 sc:阈值*大小(阈值默认0.75)
    while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
           (n = tab.length) < MAXIMUM_CAPACITY) {
        int rs = resizeStamp(n);// 生成唯一的扩容戳
        /*
        sizeCtl是数组初始化或扩容是用的控制标识位
         负数：正在进行初始化或扩容操作；
         -1：正在初始化、-N：有N-1个线程正在进行扩容操作；
         0：数组还未初始化；
         正数：初始化或下一次扩容的大小
        */
        if (sc < 0) {// sc<0，也就是 sizeCtl<0，说明已经有线程正在扩容了
            // 下面5个条件有一个true则当前线程不能协助扩容，直接跳出循环
            /*
            1. 比较高 RESIZE_STAMP_BITS 位生成戳和 rs 是否相等，相同
            2. sc == rs + 1：扩容结束
            3. sc == rs + MAX_RESIZERS：协助线程已达最大值
            4. (nt = nextTable) == null：扩容结束
            5. 所有transfer任务都被领取完，没有剩余的hash桶给当前线程做扩容
            */
            if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                transferIndex <= 0)
                break;
            // 准备协助扩容，状态更改成功则调用transfer开始协助扩容
            if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                transfer(tab, nt);
        }
        // ？？？+2
        else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                     (rs << RESIZE_STAMP_SHIFT) + 2))
            transfer(tab, null);
        s = sumCount();
    }
}
```



```java
// 生成唯一的扩容戳
// n:table的长度
private static int RESIZE_STAMP_BITS = 16;
static final int resizeStamp(int n) {
    return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
}
```





```java
// 扩容
/*
fwd:指向新表的标识，其他线程会主动跳过它，它里面的元素要么是正在进行扩容迁移、要么是已完成扩容迁移，也就是它保证线程安全再进行操作。
advance：标识是否在推进处理中，当前bucket处理完，正在处理下个bucket的标识。
finishing：标识扩容是否结束
*/
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
    int n = tab.length, stride;
    // CPU核心数大于1时（(n>>>3)/CPU核心数，结果小于16就使用16）
    /*
    目的：让CPU处理的bucket一样多，避免出现任务分配不均匀。下限：桶较少时默认一个CPU处理16个桶，即长度为16时只有1个线程来扩容。
    */
    if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
        stride = MIN_TRANSFER_STRIDE; // subdivide range
    // nextTab未初始化时（不为空时是正在扩容过程其他线程进入扩容）
    if (nextTab == null) {            // initiating
        try {
            @SuppressWarnings("unchecked")
            // 大小扩容为原来的2倍
            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
            nextTab = nt;
        } catch (Throwable ex) {      // try to cope with OOME
            sizeCtl = Integer.MAX_VALUE;
            return;
        }
        nextTable = nextTab;
        transferIndex = n;
    }
    int nextn = nextTab.length;
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    boolean advance = true;
    boolean finishing = false; // to ensure sweep before committing nextTab
    for (int i = 0, bound = 0;;) {
        Node<K,V> f; int fh;
        while (advance) {
            int nextIndex, nextBound;
            if (--i >= bound || finishing)
                advance = false;
            else if ((nextIndex = transferIndex) <= 0) {
                i = -1;
                advance = false;
            }
            else if (U.compareAndSwapInt
                     (this, TRANSFERINDEX, nextIndex,
                      nextBound = (nextIndex > stride ?
                                   nextIndex - stride : 0))) {
                bound = nextBound;
                i = nextIndex - 1;
                advance = false;
            }
        }
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            if (finishing) {
                nextTable = null;
                table = nextTab;
                sizeCtl = (n << 1) - (n >>> 1);
                return;
            }
            if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    return;
                finishing = advance = true;
                i = n; // recheck before commit
            }
        }
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        else if ((fh = f.hash) == MOVED)
            advance = true; // already processed
        else {
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    Node<K,V> ln, hn;
                    if (fh >= 0) {
                        int runBit = fh & n;
                        Node<K,V> lastRun = f;
                        for (Node<K,V> p = f.next; p != null; p = p.next) {
                            int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            ln = lastRun;
                            hn = null;
                        }
                        else {
                            hn = lastRun;
                            ln = null;
                        }
                        for (Node<K,V> p = f; p != lastRun; p = p.next) {
                            int ph = p.hash; K pk = p.key; V pv = p.val;
                            if ((ph & n) == 0)
                                ln = new Node<K,V>(ph, pk, pv, ln);
                            else
                                hn = new Node<K,V>(ph, pk, pv, hn);
                        }
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof TreeBin) {
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> lo = null, loTail = null;
                        TreeNode<K,V> hi = null, hiTail = null;
                        int lc = 0, hc = 0;
                        for (Node<K,V> e = t.first; e != null; e = e.next) {
                            int h = e.hash;
                            TreeNode<K,V> p = new TreeNode<K,V>
                                (h, e.key, e.val, null, null);
                            if ((h & n) == 0) {
                                if ((p.prev = loTail) == null)
                                    lo = p;
                                else
                                    loTail.next = p;
                                loTail = p;
                                ++lc;
                            }
                            else {
                                if ((p.prev = hiTail) == null)
                                    hi = p;
                                else
                                    hiTail.next = p;
                                hiTail = p;
                                ++hc;
                            }
                        }
                        ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                        (hc != 0) ? new TreeBin<K,V>(lo) : t;
                        hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                        (lc != 0) ? new TreeBin<K,V>(hi) : t;
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                }
            }
        }
    }
}
```







#### put()第三阶段（协助扩容）

> aaa
>

```java

final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
    Node<K,V>[] nextTab; int sc;
    if (tab != null && (f instanceof ForwardingNode) &&
        (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
        int rs = resizeStamp(tab.length);
        while (nextTab == nextTable && table == tab &&
               (sc = sizeCtl) < 0) {
            if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                sc == rs + MAX_RESIZERS || transferIndex <= 0)
                break;
            if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                transfer(tab, nextTab);
                break;
            }
        }
        return nextTab;
    }
    return table;
}
```





#### put()第四阶段







## 线程池

### 初识

**由来**

> **创建和销毁线程开销非常大**，且直接创建的线程**没办法管理**，若创建了太多的线程可能由于过度消耗资源而导致系统资源不足。
>
> 于是引入了线程池来解决这个问题，线程池主要是**线程复用**思想和**管理线程**，核心逻辑是：提前创建好若干个线程放在一个容器中（这个容器称为线程池），若有任务需要处理就直接分配给线程池中的空闲线程，任务执行完也不会销毁该线程，而是等待后续任务分配，这样就达到了对线程的复用和管理。

**优势**

> 1. **降低性能开销，**降低创建线程销毁线程的性能开销；
> 2. **避免线程过度消耗资源**，管理线程时合理设置线程池大小可避免因线程数超过硬件资源瓶颈带来的问题；
> 3. **提高响应速度**，当有新任务来时不需要等线程创建就可以让线程执行。



### 线程池构建

**设计**

> 有任务就执行任务，没有任务就阻塞（节约资源），和生产者消费者模型类似，有任务来了就放入阻塞队列中（生产），线程池中的线程去阻塞队列中取出执行（消费）。
>
> 将run()方法放入阻塞队列，而不是start()，如果是start()那么就与线程池没有啥关系了，线程池中线程不能控制执行。





线程池最核心的是ThreadPoolExecutor类，我们来看下构建线程池的参数。

```java
ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler){}
```

> 1. corePoolSize：线程池核心线程个数；
>
> 2. miximumPoolSize：线程池最大线程数个数；
>
> 3. keepAliveTime：非核心线程空闲存活时间；
>
> 4. unit：线程存活时间单位；
>
> 5. workQueue：存放待处理任务的阻塞队列；
>
> 6. threadFactory：用于创建线程的工厂，可定义个有意义的名字方便排查问题；
>
> 7. handler：线程池拒绝策略。
>
>    打个比方：corePoolSize比作公司正式员工，miximumPoolSize比作正式员工+临时员工，	

####  阻塞队列

##### ArrayBlockingQueue

> ArrayBlockingQueue（有界队列）是一个用 **数组** 实现的有界阻塞队列，按FIFO排序量。

##### LinkedBlockingQueue

> LinkedBlockingQueue（可设置容量队列）基于**链表**结构的阻塞队列，按FIFO排序任务，容量可以选择进行设置，不设置的话将是一个**无边界**的阻塞队列，最大长度为Integer.MAX_VALUE，吞吐量通常要高于ArrayBlockingQuene；newFixedThreadPool线程池使用了这个队列。

##### DelayQueue

> DelayQueue（延迟队列）是一个**任务定时周期的延迟执行**的队列。根据指定的执行时间从小到大排序，否则根据插入到队列的先后排序。newScheduledThreadPool线程池使用了这个队列。

##### PriorityBlockingQueue

> PriorityBlockingQueue（优先级队列）是具有优先级的无界阻塞队列；可根据任务自身的优先级顺序先后执行。

##### SynchronousQueue

> SynchronousQueue（同步队列，直接提交的队列）一个**不存储元素**的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQuene，newCachedThreadPool线程池使用了这个队列。
> 若使用的是该队列，则提交的任务不会被真实地保存，而总是将新任务提交给线程执行，若没有空闲的线程则创建新的线程，若线程数达到最大值则执行拒绝策略，因此使用该队列通常需要设置较大的maxmaximumPoolSize，否则很容易执行拒绝策略。

#### 拒绝策略

> 1. AbortPolicy(抛出一个异常，默认的)
> 2. DiscardPolicy(直接丢弃任务)
> 3. DiscardOldestPolicy（丢弃队列里最老的任务，将当前这个任务继续提交给线程池）
> 4. CallerRunsPolicy（交给线程池调用所在的线程进行处理)



### Java中提供的API

#### Executors

##### newFixedThreadPool

> 返回一个固定数量的线程池，当有任务提交到线程池时若有线程空闲则立即执行，若没有空闲线程则被放在任务队列中等待执行。

```java
public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>(),
                                  threadFactory);
}
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

**特点**

> 1. 核心线程数和最大线程数相等；（没有非核心线程）
> 2. 阻塞队列为无界队列（注意：队列容量的无限加大可能导致OOM）

**用途**

> 用于负载比较大的服务器，为了资源的合理利用需要限制当前线程数量。

---------



##### newCachedThreadPool

> 返回一个可根据实际情况调整线程个数的线程池，若有任务则创建线程执行任务（创建线程时不限制最大线程数量），若无任务则不创建线程，线程空闲60s后会自动回收。 
>
> 应用场景
>  	1. 淘宝订单业务:下单之后如果三十分钟之内没有付款就自动取消订单。 
>  	2. 饿了吗订餐通知:下单成功后60s之后给用户发送短信通知。
>  	3. 关闭空闲连接。服务器中，有很多客户端的连接，空闲一段时间之后需要关闭之。
>  	4. 缓存。缓存中的对象，超过了空闲时间，需要从缓存中移出。
>  	5. 任务超时处理。在网络协议滑动窗口请求应答式交互时，处理超时未响应的请求等。
>
> 参考：https://www.cnblogs.com/myseries/p/10944211.html

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>(),
                                  threadFactory);
}
```

**特点**

> 1. 核心线程数为0，最大线程数为Integer.MAX_VALUE；
> 2. 阻塞队列为SynchronousQueue

**用途**

> ？？？

---------



##### newScheduledThreadPool

> 返回一个指定线程数量的线程池，该线程池有延迟和周期性执行任务的功能，类似定时器。

```java
public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
    return new ScheduledThreadPoolExecutor(corePoolSize);
}
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue());
}

public static ScheduledExecutorService newScheduledThreadPool(
    int corePoolSize, ThreadFactory threadFactory) {
    return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
}
public ScheduledThreadPoolExecutor(int corePoolSize,
                                   ThreadFactory threadFactory) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue(), threadFactory);
}
```

**特点**

> 1. 可以延期执行

**用途**

> 一些中间件中用来做定时任务、心跳检测

---------



##### newSingleThreadExecutor

> 创建单线程化的线程池，保证所有任务按照顺序（FIFO、LIFO、优先级）执行

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

**特点**

> 1. 

**用途**

> ？？？



#### 自定义

```java
    /**
     * 线程号
     */
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger();
    /**
     * cpu数量
     */
    private static final int PROCESSOR_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池大小为cpu一半
     */
    private static final int THREAD_POLL_SIZE = Optional.of(PROCESSOR_SIZE / 2).filter(it -> it > 1).orElse(1);
    /**
     * 固定大小的线程池 保证异步任务不会影响业务系统
     */
    private static final ExecutorService SCHEDULE_EXECUTOR = new ThreadPoolExecutor(
        THREAD_POLL_SIZE,
        THREAD_POLL_SIZE,
        0L,
        TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>(),
        r -> new Thread(
            Optional.ofNullable(System.getSecurityManager())
                .map(SecurityManager::getThreadGroup)
                .orElseGet(Thread.currentThread()::getThreadGroup),
            r,
            String.format("%s-%d", "HV-EMS-EXECUTOR-POOL-THREAD", THREAD_NUMBER.getAndIncrement()),
            0
        )
    );


    /**
     * 异步执行
     * @param runnable 异步线程
     */
    public static void execute(Runnable runnable) {
        SCHEDULE_EXECUTOR.execute(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 异步回调
     * @param callable {@link Callable}
     * @param <T>      返回值类型
     * @return {@link Future}
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return
            SCHEDULE_EXECUTOR.submit(() -> {
                try {
                    return callable.call();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new UtilException(e.getMessage());
                }
            });
    }
```



### 线程池原理分析

![线程池整体理解](.\images\线程池整体理解.png)

```java
private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         */
//      ctl：原子类记录线程池状态和线程数量，int(32)高3位标识线程状态、低29位标识线程数（通过系列位运算）
        int c = ctl.get();
	    // 1.线程池中线程数小于核心线程数则创建线程并把任务给该线程执行
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
	    // 2.核心线程已满，将任务添加到队列
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get(); // double-check
            // 若线程池已没有运行了就将该任务从队列中移除，然后执行拒绝策略
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
    	// 3.若队列满了，创建临时线程，若创建失败（超过了最大线程数量）则执行拒绝策略
        else if (!addWorker(command, false))
            reject(command);
    }
```

##### 创建线程并执行任务

addWorker

> - 判断线程数是否超过阈值
> - 构建线程并启动
> - 统计当前工作线程数量，考虑线程安全性
> - 存储的容器（HashSet<Worker>）
>
> 注意：线程池在构建时并没有初始化核心线程，根据添加的任务来启动核心线程

```java

private final HashSet<Worker> workers = new HashSet<Worker>();
private boolean addWorker(Runnable firstTask, boolean core) {
    retry:
    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // Check if queue empty only if necessary.
        if (rs >= SHUTDOWN &&
            ! (rs == SHUTDOWN &&
               firstTask == null &&
               ! workQueue.isEmpty()))
            return false;

        for (;;) {
            int wc = workerCountOf(c);
            if (wc >= CAPACITY ||
                wc >= (core ? corePoolSize : maximumPoolSize))
                return false;
            if (compareAndIncrementWorkerCount(c))
                break retry;
            c = ctl.get();  // Re-read ctl
            if (runStateOf(c) != rs)
                continue retry;
            // else CAS failed due to workerCount change; retry inner loop
        }
    }

    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null;
    try {
        w = new Worker(firstTask); // 实例化worker
        final Thread t = w.thread;
        if (t != null) {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock(); // 加锁保证安全性
            try {
                // Recheck while holding lock.
                // Back out on ThreadFactory failure or if
                // shut down before lock acquired.
                int rs = runStateOf(ctl.get());

                if (rs < SHUTDOWN ||
                    (rs == SHUTDOWN && firstTask == null)) {
                    if (t.isAlive()) // precheck that t is startable
                        throw new IllegalThreadStateException();
                    workers.add(w);// 添加到池中
                    int s = workers.size();
                    if (s > largestPoolSize)
                        largestPoolSize = s;
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) { // 添加成功之后启动线程
                t.start(); // 启动之后就在ThreadPoolExecutor中的run方法执行
                workerStarted = true;
            }
        }
    } finally {
        if (! workerStarted) // 操作失败需要回滚数据
            addWorkerFailed(w);
    }
    return workerStarted;
}

public void run() {
    runWorker(this);
}
```

##### 线程复用与回收

> **线程复用：**
>
> 主要就是**线程开启后通过while循环不断地阻塞式从任务队列中获取任务，然后执行任务中的run()**，注意是run()不是start()（调start()必定对新开个线程，就做不到线程复用了）。
>
> **线程回收：**
>
> 线程只要run()执行结束后该线程就即将被销毁，而线程池中的线程是在while循环中执行的run()，所以回收线程时就让while循环结束即可。
>
> 核心线程和临时线程没有区分，回收线程时只需要从数量上保证核心线程个数就行了。
>
> 
>

```java
final void runWorker(Worker w) {
    Thread wt = Thread.currentThread();
    Runnable task = w.firstTask;
    w.firstTask = null;
    w.unlock(); // allow interrupts
    boolean completedAbruptly = true;
    try {
        // 线程回收时让getTask()的值为空即可
        while (task != null || (task = getTask()) != null) {
            w.lock();
            // If pool is stopping, ensure thread is interrupted;
            // if not, ensure thread is not interrupted.  This
            // requires a recheck in second case to deal with
            // shutdownNow race while clearing interrupt
            if ((runStateAtLeast(ctl.get(), STOP) ||
                 (Thread.interrupted() &&
                  runStateAtLeast(ctl.get(), STOP))) &&
                !wt.isInterrupted())
                wt.interrupt();
            try {
                beforeExecute(wt, task);
                Throwable thrown = null;
                try {
                    task.run(); // 拿到这个任务，执行该任务的run方法，注意是run()不是start()
                } catch (RuntimeException x) {
                    thrown = x; throw x;
                } catch (Error x) {
                    thrown = x; throw x;
                } catch (Throwable x) {
                    thrown = x; throw new Error(x);
                } finally {
                    afterExecute(task, thrown);
                }
            } finally {
                task = null;
                w.completedTasks++;
                w.unlock();
            }
        }
        completedAbruptly = false;
    } finally {
        processWorkerExit(w, completedAbruptly);
    }
}
```



```java
private Runnable getTask() {
    boolean timedOut = false; // Did the last poll() time out?

    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // Check if queue empty only if necessary.
        if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
            decrementWorkerCount();
            return null;
        }

        int wc = workerCountOf(c);

        // Are workers subject to culling?
        // 控制线程回收
        boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

        if ((wc > maximumPoolSize || (timed && timedOut))
            && (wc > 1 || workQueue.isEmpty())) {
            if (compareAndDecrementWorkerCount(c))
                return null;
            continue;
        }

        try {
            Runnable r = timed ?
                workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
            workQueue.take(); // take()是阻塞的
            if (r != null)
                return r;
            timedOut = true;
        } catch (InterruptedException retry) {
            timedOut = false;
        }
    }
}
```



### 线程池大小设定

**CPU密集型：**

> 线程需要CPU运算速率很高，完成依赖于CPU计算的线程，**需要利用的CPU资源非常的多**。
>
> 说明CPU资源比较紧张，**保持和CPU核心数量一致**较合适，比如8核的CPU就设置8个线程。

**IO密集型：**

> 更多是基于IO处理，基于IO的处理线程很多的时候CPU处于等待状态，说明**需要利用的CPU资源不那么多**。
>
> CPU资源就比较宽裕。多设置一些线程能提高CPU利用率，**核心数*2**合适。

线程池设定的等待时间 + 线程CPU时间 / 



实现线程池的一些监控，可以继承ThreadPoolExecutor，然后重写里面的prestartCoreThread等方法可以把线程数据上报到监控平台。



### 使用注意事项

##### 阿里开发手册不建议使用JDK提供的默认实现

> 1. 阻塞队列为无界队列，当大量请求堆积到队列中时可能导致OOM
> 2. 允许创建线程的数量为Integer.MAX_VALUE，可能会有大量线程创建出现CPU使用过高或OOM

------



##### 如何配置线程池大小

> 

----------------









### Callable/Future使用及原理分析



> Callable/Future与Thread比较最大的差异在于能够很方便的获取线程执行完后的结果。
>
> 什么时候需要回调接口？因为线程时异步执行的，并不知道结果什么时候计算完成，所以它传递一个回调接口给计算线程，当计算完成时调用这个回调接口，收到回传的结果值。
>
> **应用场景**
>
> Dubbo的异步调用，消息中间件的异步通信等。



#### FutureTask



![FutureTask类图](.\images\FutureTask类图.png)

> 它实现了Runnable和Future两个接口，Runnable是**执行任务**的接口相当于生产者，Future是**获取任务结果**以及取消任务等相当于消费者，所以实际上FutureTask类可以理解成一个生产者消费者模型的多线程操纵类。









```java
// 状态分析
private volatile int state;
/*
 * 新建状态，还未运行
 */
private static final int NEW          = 0;
/*
 * 完成状态，但还有后序操作（如唤醒等待线程）
 */
private static final int COMPLETING   = 1;
/*
 * 完结状态，正常完成，没有发生异常
 */
private static final int NORMAL       = 2;
/*
 * 完结状态，发生异常而完结
 */
private static final int EXCEPTIONAL  = 3;
/*
 * 完结状态，取消任务而完结
 */
private static final int CANCELLED    = 4;
/*
 * 完结状态，发起中断请求中断任务了而完结，
 */
private static final int INTERRUPTING = 5;
/*
 * 完结状态，完成了中断任务的中断请求而完结
 */
private static final int INTERRUPTED  = 6;
```







### 线程池对Callable/Future的执行





## 题

### 综合类

#### 三线程循环输出ABC

**题目**

> 有A、B、C 三个线程，A线程 输出“A”，B线程 输出“B”，C线程 输出“C”，要求同时启动3个线程，按照顺序输出“ABC”，循环10次，请使用代码实现。

**分析**

> 该题关键在于控制线程输出顺序，保持顺序要么用**计数器**（计数器累加，每个线程都有个数字标识(从0开始递增顺序0:A 1:B)，比如n%3==0就轮到Thread-A输出，n%3==1就轮到Thread-B输出，以此类推随着n的累加就会依次输出想要的结果）；要么用**链式**方式保持顺序，每个线程记录下它的后继线程。
>
> 保证顺序的过程中还需要考虑多线程环境下**数据一致性**带来的问题，在使用计数器时保证多线程环境下数据一致就需要加锁，可以选择**synchronized锁类对象、或者Lock锁类对象，配合wait/notifyAll**使用。若不使用锁，使用**原子类计数**也可保证数据一致。

##### 方法一

> 基于原子类的计数器

```java
private static       AtomicInteger atomicCounter = new AtomicInteger(0);
private static final int           MAX           = 30;

void solution1() {
    ExecutorService service = Executors.newFixedThreadPool(3);
    service.execute(new MyRunnable(0, "A"));
    service.execute(new MyRunnable(1, "B"));
    service.execute(new MyRunnable(2, "C"));
    service.shutdown();
}

class MyRunnable implements Runnable {
    /**
    * 标识ABC三个线程，0:A;   1:B;   2:C;
    */
    private int    flag;
    private String value;

    public MyRunnable(int flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    @Override
    public void run() {
        while (atomicCounter.get() < MAX) {
            // System.out.println("===" + Thread.currentThread().getName() + "===");
            // 任由CPU怎么调度，反正只在输出结果这儿把握好就OK。用原子类变量就可以不用加锁操作
            if (atomicCounter.get() % 3 == flag) {
                System.out.println(value);
                atomicCounter.incrementAndGet();
            }
        }
    }
}
```

##### 方法二

> 普通计数器+lock锁类对象（用synchronized锁类对象的代码类似）

```java
void solution2() {
    new MyThread(0, "A").start();
    new MyThread(1, "B").start();
    new MyThread(2, "C").start();
}

class MyThread extends Thread {
    /**
     * 多个线程实例共有（类变量），不用volatile修饰，操作该值时是加锁状态不存在多个线程同时修改
     * 该变量累加，结合线程标识状态控制打印顺序
     */
    private static int       counter;
    /**
     * 标识ABC三个线程，0:A;   1:B;   2:C;
     */
    private        int       flag;
    private        String    value;
    /**
     * 必须声明成类变量，需要加类级别的锁（这儿对象级别的锁在实例化线程时每个线程都会有个，显然锁不住）
     */
    private static Lock      lock      = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public MyThread(int flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // 1.加锁
            lock.lock();
            // 2.判断是否轮到当前线程，没有轮到则将其阻塞
            // 注意这儿要用while，把线程批量唤醒后，如果不是该轮到的线程要让其继续阻塞
            while (counter % 3 != flag) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 执行到这里，表明轮到当前线程了，输出结果
            System.out.println(value);
            // 输出后累加计数器，并批量唤醒线程，最后解锁
            counter++;
            condition.signalAll();
            lock.unlock();
        }
    }
}
```

##### 方法三

> 设置线程的后继线程+synchronized锁类对象

```java
private static MyLinkedThread headThread;
private static MyLinkedThread nextThread;

void solution3() {
    // 设置线程间的链式关系
    MyLinkedThread c = new MyLinkedThread("C");
    MyLinkedThread b = new MyLinkedThread(c, "B");
    MyLinkedThread a = new MyLinkedThread(b, "A");
    // 初始化线程的头节点和下个节点（从a线程开始）
    headThread = nextThread = a;
    a.start();
    b.start();
    c.start();
}

class MyLinkedThread extends Thread {
    /**
    * 标识线程的后继线程
    */
    private MyLinkedThread next;
    private String         value;

    public MyLinkedThread(MyLinkedThread next, String value) {
        this.next = next;
        this.value = value;
    }

    public MyLinkedThread(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (MyLinkedThread.class) {
                // 当前顺序不应该当前线程执行，则将其阻塞
                Thread currentThread = Thread.currentThread();
                while (!currentThread.equals(nextThread)) {
                    try {
                        MyLinkedThread.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 执行到这里，表明满足条件，打印
                System.out.println(value);
                // 下个节点为空时，就指向头节点以构成循环
                nextThread = next == null ? headThread : next;
                // 调用notifyAll方法
                MyLinkedThread.class.notifyAll();
            }
        }
    }
}
```

