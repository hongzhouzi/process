# SpringMVC初体验

### Spring前身前世与思想

#### Spring的前世今生

##### Spring的设计初衷

> Spring是为解决企业级应用开发的复杂性而设计，她的使命是：“简化开发”。有许多框架在某些方面做了简化，而Spring则立志于全面的简化Java开发，还充当了粘合剂的作用，可以将许多优秀框架整合在一起使用。
>
> 4个关键策略：
>
> 1. 基于POJO的轻量级和最小入侵性编程
>
>    （如：一个类若要支持动态代理，原生JDK层面就必须实现一个接口作为条件，但Spring层面可以不要任何条件，用CGlib满足；类有复制操作，JDK层面就必须实现Cloneable接口，Spring层面就不需要任何条件，用原型模式满足）
>
> 2. 通过依赖注入和面向接口松耦合
>
>    （不要手动new对象）
>
> 3. 基于切面和惯性进行声明式编程 
>
>    声明式的代码增强，把公共操作全部放在切面；惯性声明式编程：更符合我们编程习惯的可见的自由配置编程，比如通过xml配置
>
> 4. 通过切面和模板减少样板式代码

##### BOP编程伊始

> Bean Oriented Programming（面向bean编程）Spring中所有都是bean（Java普通类），bean放在spring容器中，spring容器本身也是bean。
>
> 对bean的控制（bean创建、销毁、管理）
>
> BeanFactory接口（工厂模式实现）允许通过名称创建和检索对象，也可管理对象之间关系。它最底层支持两个对象模型。
>
> 1. 单例：提供了具有特定类名的全局共享实例对象，可以在查询时对其进行检索，它是默认的也是最常用的对象模型。
> 2. 原型：确保每次检索都会创建单独的实例对象。在每个用户都需要自己的对象时采用原型模式。

##### AOP编程理念

> 面向切面编程，核心是构造切面，将那些影响多个类且与具体业务无关的行为封装到可重用的模块中来。
>
> AOP编程常用的场景有：Authentication（权限认证）、Logging（日志记录）、Transactions（事务处理）、Auto Caching（自动缓存处理）、Error Handling（统一错误处理）、Debugging（调试信息输出）等。

#### Spring中的编程思想总总结

| Spring思想 | 应用场景（特点）                                 | 归纳        |
| -------- | ---------------------------------------- | --------- |
| OOP      | 面向对象编程(Object Oriented Programming)用程序归纳总结生活中的一切事物。 | 继承、封装、多态  |
| BOP      | 面向Bean编程(Bean Oriented Programming)面向bean(普通java类)设计程序 | 一切从bean开始 |
| AOP      | 面向切面编程(Aspect Oriented Programming)找出多个类中有一定规律的代码，开发时拆开，运行时再合并(动态代理) | 解耦，专人做专事  |
| IOC      | 控制反转(Inversion of Control)将new对象的动作交给spring管理，并由spring保存已创建的对象(IOC容器) | 转交控制权     |
| DI/DL    | 依赖注入(Dependency Injection)依赖查找(Dependency Lookup)Spring不仅保存自己创建的对象还保存对象之间的关系。注入即赋值，有三种方式：构造方法、set方法、直接赋值。 | 赋值        |
|          |                                          |           |

#### Spring 5系统架构

> 以support结尾的模块都是提供扩展支持的模块

##### 

#### Spring版本命名规则

##### 语义化版本命名通用规则

##### 商业软件中常见的修饰词

##### 软件版本号使用限定

##### Spring 版本命名规则







#### 思考SpringMVC的设计

**解决痛点**

> 直接在servlet上编写Controller太繁琐了，每编写一个servlet类都需要在xml中配置、且servlet类继承servlet官方提供的类并重写里面的一些方法。随着业务越来越复杂servlet类也会越来越多、配置也会越来越臃肿，非常不好管理，后期维护就变得异常困难。

**需求** 

> 解决上面痛点，基于servlet开发将配置和开发中

**技术方案**

> 1. 容器化技术，对所有servlet类进行统一管理
>
> 2. 请求分发，有请求来时，统一交给一个类将请求分发给对应的类处理
>
>    整体思路：容器启动时扫描注解，注册请求URL和对应的处理方法，请求时根据请求的URL定位到具体的处理方法；处理类中通过注解中加上对应URL，在反射方式拿到注解URL下的方法然后用反射调用该处理方法。

**实现案例**



### Spring5注解编程基础组件

#### 配置组件

> - @Configuration：把类作为一个IOC容器，它的某个方法头上个若注册了@Bean就会作为这个Spring。（注解中可指定value，includeFilter，useDefaultFilter）
> - @ComponentScan：在配置类上添加该注解，默认就会扫描该类所在的包下所有的配置类，相当于<<content:component-scan>>
> - @Scope：用于指定scope作用域（用在类上）
> - @Lazy：表示延迟初始化，单例bean才延迟加载，不配默认非延迟加载（容器启动时不加载，调用时才加载）
> - @Condition：spring4开始提供支持，按照一定条件进行判断，满足条件才将该bean注册进IOC容器。配合继承Condition类的带有判断条件的类使用
> - @Import：导入外部资源
> - 生命周期控制
>   - @PostConstruct：用于指定初始化方法（用在方法上）
>   - @PreDestroy：用于指定销毁方法（用在方法上）
>   - @DependsOn：定义bean初始化及销毁时的顺序

#### 赋值（自动装配）组件

> **给IOC容器注册bean的方式**
>
> 1. @Bean 直接导入单个类
>
> 2. @ComponentScan 默认扫描@Controller @Service @Repostory @Component
>
> 3. @Import 快速给容器导入bean。 @Import直接导入、或者实现ImportSelector自定义规则实现、或者实现ImportDefinitionRegistrar获得BeanDefinitionRegistry可以手动直接往IOC容器中塞
>
> 4. FactoryBean把需要注册的对象封装为FactoryBean。FactoryBean负责将bean注册到IOC的bean，BeanFactory从IOC容器中获得Bean对象。
>
>    ​
>
>    **对Bean生命周期的监控**
>
>    1. 配置@Bean的参数init 
>    2. 分别实现InitializingBean和DisposableBean接口
>    3. 使用@PostConstruct和@PreDestroy注解
>    4. 自定义一个类，实现BeanPostProcessor接口
>
>    ​
>
>    @Value：支持基本数据类型、Spring EL 表达式
>
>    ```java
>    // 基本数据类型
>    @Value("XXX")
>    // spring el表达式
>    @Value("#{8-5}")
>    // 从环境变量，配置文件中取值
>    Environment em = applicationContext.getEnvironment();
>    @Value("${env.field}")
>
>    ```
>
>    - @Component：泛指组件
>    - @Service：标注业务层组件
>    - @Controller：标注控制层组件
>    - @Repository：标注数据访问层组件
>    - @PropertySource：读取配置文件赋值
>    - @Value：普通数据类型赋值
>    - @Qualifier：给实例取名用，若存在多个实例基本都要用它
>    - @Autowired：默认按类型装配，若想按名称装配可以结合@Qualifier使用
>    - @Resources：默认按名称装配，找不到名称匹配的bean就按类型装配
>    - @Primary：自动装配时出现多个bean候选时，加了该注解的作为首选，否则将异常
>
>    ​
>
>    ​

