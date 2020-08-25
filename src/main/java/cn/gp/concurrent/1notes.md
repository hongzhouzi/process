#### 前言

> 设计模式最重要的是**解耦**，学习过程主要学习设计模式是如何**总结经验并将经验为自己所用**，同时锻炼将业务需求转换技术实现的一种有效方式。

## 多线程基础

#### 线程的启动

new Thread.start(); // 启动线程

run(); // 直接调用方法

> Java是不提供线程的，只是对底层语言进行了封装，调用start方法本质是在调用本地start0()方法启动线程，JVM再根据不同的操作系统创建对应操作系统上的线程再启动线程，轮到该线程执行时（等待操作系统调度算法选中）再从操作系统层面调用JVM中的run方法，JVM中的run方法再调用Java高级语言写的run方法。执行完后再由JVM销毁该线程。

![调用start](.\images\调用start.png)

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
> ![线程中断](.\images\线程中断.png)

#### 线程状态转化

> 创建线程后为初始状态【**NEW**】，调用start方法启动线程后就绪，当该线程被系统调度选中后变为运行中，就绪和运行中都为运行状态【**RUNNABLE**】，若此时调用了阻塞线程的方法（Object.wait()、Object.join()、LockSupport.park()）后变成等待状态【**WAITING**】，若调用的阻塞线程的方法中指定了时间（Thread.sleep(long)、Object.wait(long)、Object.join(long)、LockSupport.parkNanos()、LockSupport.parkUntil()）就变成超时等待状态【**TIMED_WAITING**】,直到调用了唤醒线程的方法（Object.notify()、Object.notifyAll()、LockSupport.unpark(Thread)）或者阻塞时间到了线程又进入运行状态，当多个线程在竞争同一把锁时没有竞争赢的线程就进入阻塞状态【**BLOCKING**】，若线程执行完了就进入终止状态【**TERMINATED**】。
>
> 1、NEW(初始状态)：高级语言层面的线程创建，操作系统并没有真正创建线程，因此也不会获得CPU的执行权，通过调用start()方法进入RUNNABLE状态；
> 2、RUNNABLE(可运行/运行状态）：RUNNABLE状态分可运行和运行两种状态，在java语言中统一叫做RUNNABLE状态，NEW状态的线程通过调用start()方法进入可运行状态，此时操作系统会新建一个线程，当有CPU空闲时，OS会分配CPU执行权，此时进入运行状态。运行时状态也可通过调用yield()方法再次进入可运行状态；
> 3、BLOCKED(阻塞状态）：RUNNABLE状态的线程如果遇到同步锁等待的情况，会进入BLOCKED状态，此时会让出CPU执行权。若同步锁释放，当前线程重新获取CPU执行权并获取锁，此时会从BLOCKED状态重新进入RUNNABLE状态；
> 4、WAITING(无限时等待）：RUNNABLE状态的线程内部调用wait()、sleep()、join()、LockSupport.part()等方法时，会从RUNNABLE状态进入WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 5、TIMED_WAITING(有限时等待）：RUNNABLE状态的线程内部调用wait(long)、sleep(long)、join(long)、LockSupport.partUnit(xx)方法时，会从RUNNABLE状态进入TIMED_WAITING状态，并出让CPU使用权，此时线程可以通过调用notify()、notifyAll()、LockSupport.unpart()方法进次入RUNNABLE状态；
> 6、TERMINATED(终止）：线程运行结束，进入终止状态。也可以通过调用stop()或interrupt()方法进入终止状态，不过stop()方法会直接杀死线程，当前线程持有的锁也不会释放，该方法时不可取的，interrupt()方法仅是通知线程可以终止，具体是否要终止有线程内部自己决定。interrupt()方法会更新线程标识位，并且抛出InterruptedException，线程内部可以通过主动检查线程标志位或捕获InterruptException的方式来获取线程终止的通知。



#### 应用

> - 文件等资源上传业务
> - 系统间数据同步操作、数据解析、原始消息文件入库操作。
> - 系统中的日志记录。
> - 发送短信、推送通知、用户注册后送积分、

## 并发

#### 锁

> 本质是共享资源，synchronized放在方法上、代码块上，加锁保证线程安全性。
>
> 类锁：方法加了static修饰的、XX.class，静态锁、类对象；资源供多个对象访问时就需要加类锁，针对所有对象都互斥。
>
> 实例锁：对象实例，只针对当前对象实例有效。锁this、没有static修饰的方法。
>
> 锁的互斥性： 在锁实例对象时要锁同一个实例，若锁的不同实例那么锁就没有意义了。当多个线程访问资源时，看访问时锁住的是不是同一个东西，是同一个东西锁才是互斥的。类锁和对象锁主要的差异也就是锁的互斥范围。

#### 锁的存储（对象头）

> 对象头：对象在内存中的布局。