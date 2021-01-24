# SpringBoot

> 分布式的两个条件：服务之间远程通信
>
> 集群：高可用（防止单点故障）、提高性能

注册中心：服务地址的管理

服务动态感知（上线、下线、恢复、扩容）

配置中心：管理数据库配置、常量配置、线程池大小、服务降级开关、阈值（限流阈值、线程池阈值）、密钥等。

API网关：统一授权、日志的收集、权限认证、限流、熔断。（filter、route）







## SpringBoot

基于Spring，对配置进行轻量化。

没有SpringBoot时，用springMVC构建项目

1. 创建项目结构（maven/gradle）
2. spring依赖，相关技术栈用到的依赖
3. 配置web.xml，DispatcherServlet.xml
4. 发布到容器

思考将这些提取成**通用模板，脚手架工程**。



SpringBoot

> 约定优于配置理念下的产物。
>
> - 只要依赖了spring-boot-starter-web的jar就会自动内置一个Tomcat容器
> - 默认的项目结构
> - 默认的配置文件application.properties
> - starter启动依赖，如果是webstarter则默认构建一个spring MVC的应用



### SpringBoot特性

> - EnableAutoConfiguration 自动装配
> - Starter 启动依赖，依赖于自动装配技术
> - Actuator 监控，提供了一些Endpoint、http、jmx形式去访问，health信息、metric信息
> - Spring Boot CL（命令行操作的功能，groovy脚本）





Spring3.x版本

> @Confignation
>
> 去xml化，核心是把bean对象以更加便捷的方式注入到IOC容器中。能够代替用xml配置实现的功能。
>
> @Import 替代xml中impart标签

> @Enable
>
> 模块驱动，启动一个模块，把相关组件的bean自动装配到IOC容器中。（比如定时任务模块的加上EnableSchedule注解就会将定时任务相关注解扫描装配到IOC容器，就不用手动配置了，之前这种是需要在xml中配置的）
>
> 支持自定义EnableXX

spring4.X

> @Condition
>
> 在某个条件下装载

spring5.X

> @Indexed
>
> 在性能上做的优化

### 核心原理——自动装配

要达到开箱即用的效果，就需要把相关bean自动装配到IOC容器中，spring只是个IOC容器，得通过一定的方式将组件动态的自动装配到IOC容器中才可达到引入组件之后即可使用（开箱即用）。

如何实现批量扫描？

动态bean的装载：

> ImportSelector：DeferredImportSelector
>
> Registator：ImportBeanDefinitionRegistrar

通过实现ImportSelector接口，在重新的方法中写需要导入的配置类或bean的getName()（动态装载），然后返回该字符串数组。

```java
public class MyDefineImportSelector implements ImportSelector{
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //动态导入bean, 告诉了Spring  ， 两个配置类在哪里

        //TODO 在这里去加载所有的配置类就行？
        // 通过某种机制去完成指定路径的配置类的扫描就行？
        //package.class.classname
        return new String[]{MySqlSessionFactory.class.getName(), RedisConfiguration.class.getName()};
    }
}
```

这儿用ComponentScan(basePackage)不能实现，因为并不知道配置类在哪儿。用Import是因为有这个api动态加载bean（动态读取配置后加载），而ComponentScan不能动态加载。

标准规范：扫描classpath:MATA-INF/spring.factories文件

```c
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  配置类全路径
```



通过扫描该文件得到配置信息，在加载过程通过SpringFactoriesLoader来加载。

SpringFactoriesLoader是一种SPI机制的思想。

看源码类：SpringBootApplication -> EnableAutoConfiguration -> AutoConfigurationImportSelector



SPI机制

Service provider interface

满足以下条件：

- 在classpath目录下创建一个META-INF/services目录
- 在该目录下创建一个扩展点的全路径名
  - 文件中添加这个扩展点的实现（在扫描时通过该全路径名就能找到里面有那些扩展点）
  - 文件编码格式是UTF-8
- 使用时用ServiceLoader去进行加载





条件控制

对于官方的starter组件

- 官方包 spring-boot-starter-xxx
- 第三方包 xxx-spring-boot-starter

对于官方包的starter中的配置文件在spring-boot-autoconfigure中，没有在单独的starter配置文件中。官方包这种配置是一种条件触发（ConditionXXX）

条件装配有两种表现方式：

1. 加注解指定，内容是 `ConditionOnClass(XX.class)` 表示本类要满足XX.class存在的情况下再加载本类
2. 通过spring-autoconfigure-metadata.properties配置文件指定，内容是：`配置类全路径.ConditionalOnClass=XXX` 表示本配置类要满足XXX存在的情况下再加载本类

编写Starter要点

> - 配置类（@Configure和@Bean注解）
>
> - MATA-INF/spring.factories配置文件，里面指定了配置类的路径
>
>   
>
>   若有需要定义的配置文件（如定义Redis的IP地址、端口等）配置文件提示可以用`spring-boot-configuration-processor`组件来实现（需要在MATA-INF/additional-spring-configuration-metadata.json文件中添加内容）
>
>   ```json
>   {
>     "properties": [
>       {
>         "name": "my.redisson.host",
>         "type": "java.lang.String",
>         "description": "redis的服务器地址",
>         "defaultValue": "localhost"
>       },{
>         "name": "my.redisson.port",
>         "type": "java.lang.Integer",
>         "description": "redis服务器的端口",
>         "defaultValue": 6379
>       }
>     ]
>   }
>   ```
>
>   

springboot处理流程

> 有一个实现了ImportSelector接口的类专门处理spring.factories中的配置信息，动态将bean加载到容器中，通过该配置信息将starter组件注册到IOC容器。其中用到了SPI机制的思想，另外官方的starter和三方的starter有一定区别，官方的配置信息统一在一个地方指定的，这儿还用到了条件控制；三方的配置信息就写在该spring.factories中的。

几个关键词

> - ImportSelector（动态配置类）
> - SpringFactoriesLoader（获取spring/factories配置文件内容）
> - @Configuration（配置类）
> - @Conditional（条件控制）

### 编写Starter

#### 目标

编写基于redisson的自定义连接Redis的组件

#### 代码

##### 依赖

```xml
<dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
      <version>2.3.1.RELEASE</version>
      <!-- 是否传递依赖 -->
      <optional>true</optional>
    </dependency>
    <!-- redisson -->
    <dependency>
      <groupId>org.redisson</groupId>
      <artifactId>redisson</artifactId>
      <version>3.13.1</version>
    </dependency>
    <!-- 用于配置文件提示 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <version>2.3.1.RELEASE</version>
    </dependency>
  </dependencies>
```

##### 类

###### 实体

```java
/**
 * 配置属性实体类
 **/
@ConfigurationProperties(prefix = "my.redisson")
@Data
public class RedissonProperties {
    private String host="localhost";
    private int port=6379;
    private int timeout;
    private boolean ssl;
}
```

###### 配置类

```java
// 条件装配
@ConditionalOnClass(Redisson.class)
// 模块驱动，加载属性配置，且注入的属性时RedissonProperties时启用，因为下面注册本bean依赖这个属性
@EnableConfigurationProperties(RedissonProperties.class)
// 声明为配置类
@Configuration
public class RedissonAutoConfiguration {

    @Bean
    RedissonClient redissonClient(RedissonProperties redissonProperties){
        Config config=new Config();
        String prefix="redis://";
        if(redissonProperties.isSsl()){
            prefix="rediss://";
        }
        config.useSingleServer().
                setAddress(prefix+redissonProperties.getHost()+":"+redissonProperties.getPort()).
                setConnectTimeout(redissonProperties.getTimeout());

        return Redisson.create(config);
    }
}
```

###### 配置文件

META-INF/spring.factories文件内容

```xml
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.my.redisson.RedissonAutoConfiguration
```

additional-spring-configuration-metadata.json文件内容（配置提示类）

```json
{
  "properties": [
    {
      "name": "my.redisson.host",
      "type": "java.lang.String",
      "description": "redis的服务器地址",
      "defaultValue": "localhost"
    },{
      "name": "my.redisson.port",
      "type": "java.lang.Integer",
      "description": "redis服务器的端口",
      "defaultValue": 6379
    }
  ]
}
```

##### 结语

这样就编写好了，在使用时按照常规的starter使用即可。





### [Actualor](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints)

springboot中的应用监控机制

依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

两种形态的监控

> - http（web）
> - jmx

#### JMX

> JXM全称是 Java Management Extensions（Java管理扩展），提供了对Java应用程序和JVM的监控及管理。通过JMX可以监控
>
> 1. 服务器中的各种资源的使用情况、CPU、内存
> 2. JVM内存使用情况、线程使用情况

SpringBoot的系列监控信息可以发布到Prometheus+Grafana中



#### Prometheus

开源的监控系统

##### 安装

> 1. 下载Prometheus，https://github.com/prometheus/prometheus/releases
>
> 2. tar -zxvf prometheus-2.19.1.linux-amd64
>
> 3. 修改prometheus.yml，增加需要监控的应用节点
>
>    ```yml
>    scrape_configs:
>     # The job name is added as a label `job=<job_name>` to any timeseries
>    scraped from this config.
>    - job_name: 'prometheus'
>      # metrics_path defaults to '/metrics'
>      # scheme defaults to 'http'.
>     static_configs:
>     - targets: ['localhost:9090']
>    - job_name: 'spring-actuator'
>     metrics_path: '/actuator/prometheus'
>     scrape_interval: 5s
>     static_configs:
>     - targets: ['192.168.8.174:8080']  #需要监控的应用节点
>    ```
>
>    参数解释：	
>
>    1. job_name：任务名称
>    2. metrics_path： 指标路径
>    3. targets：实例地址/项目地址，可配置多个
>    4. scrape_interval： 多久采集一次
>    5. scrape_timeout： 采集超时时间
>
> 4. 执行 ./prometheus --config.file=prometheus.yml 启动prometheus应用，访问：http://HOST_IP:9090
>
> 5. nohup prometheus &

##### SpringBoot集成

###### 依赖

```xml
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

> 









分片

对存储和计算分片。存储分片有mycat、sharding-jdbc框架



为了满足高可靠性，就得有“冗余（replication）副本机制”，但又引出了“副本一致性问题”，于是又有很多理论和算法来保证副本一致性。







### RabbitMQ



1. 异步通信：比如公司中各个业务系统与数据中心间的数据同步就需要用异步通信的方式来实现。以及银行转账，相当于各个系统间的异步调用操作同样需要异步通信。

2. 系统解耦：在分布式系统中，一个业务流程涉及到多个系统时他们之间就会形成一个依赖关系（如：订单系统依赖于：库存系统、支付系统、通知系统）。订单系统中一个订票成功的操作，对于其他系统都会有相应的动作，而其他系统之间的动作没有直接的依赖关系和先后顺序。这种情况就可以用并行方式去处理，可以引入多线程处理，但这种方式会让有依赖关系的系统间耦合度很高，后期如果增减业务系统就必定会调整代码。如果用MQ则只需要修改MQ相关配置即可。

3. 流量削锋：防止突然大量流量冲过来服务器处理不了而宕机，引入MQ后流量依次进入到MQ中排队，服务器从MQ取出数据尽量处理即可，而不会直接处理不了大量请求而宕机。
4. 广播通信：一对多的通信，和上面系统解耦差不多的例子。



AMQT协议，很多MQ都遵守这个协议，在spring中使用rabbitMQ时就还需要进入amqt的包，在这个协议的接口上做对rabbitmq做封装。





计划：

整理springboot笔记（周一上午）、学习MQ和整理MQ笔记（r周二、k周四、周五看activeMQ在公司项目中的应用）

在复习时，每个知识点都争取结合在项目中的应用来记忆（优先说公司中参与过的项目，再是学习到的应用例子）



时间够可了解：分库分表；spring cloud-feign、授权登录、全局id；spring cloud alibaba-分布式授权OAuth2.0