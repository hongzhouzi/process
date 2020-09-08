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
> 简而言之，**volatile底层是通过防止指令重排序的机制和内存屏障的机制保证可见性的**。
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
>}
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

# 线程基础回顾

## 死锁/活锁

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

#### ThreadLocal

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

### 思考锁的实现（设计思维）

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

![线程添加到阻塞队列过程](.\images\线程添加到阻塞队列过程.png)



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

// 锁自旋
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor(); // 头节点的下个节点线程醒了，自旋时p=头节点
            if (p == head && tryAcquire(arg)) { // 头节点尝试去获得锁
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

![释放锁时线程自旋过程](.\images\释放锁时线程自旋过程.png)

**抢占释放锁整体过程：**

![抢占释放锁整体过程](.\images\抢占释放锁整体过程.png)

##### **锁的公平性：**

> 非公平锁在抢占锁时，执行到 lock() 就去抢占锁，没有抢占到锁就去阻塞队列排队。而公平锁执行到 lock() 不会直接抢占锁，会判断阻塞队列里是否有值，队列为空才去抢占锁，队列不为空就在阻塞队列排队。

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

