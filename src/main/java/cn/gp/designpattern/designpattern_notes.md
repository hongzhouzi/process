### 前言

> 设计模式最重要的是**解耦**，学习过程主要学习设计模式是如何**总结经验并将经验为自己所用**，同时锻炼将业务需求转换技术实现的一种有效方式。

## 七大软件设计原则

> 设计原则是**设计模式的基础**，实际开发中**并不是所有代码都必须遵循设计原则**，它只是一种指导思想，在适当的场景遵守设计原则来帮助我们设计出更加优雅的代码，而不是强加硬套上去硬生生把代码变臃肿。

### 开闭原则

> 解决问题：版本更新时，尽可能不修改源代码，以增加源代码的方式增加新功能。
>
> 原则：一个实体类、模块、函数应该**对扩展开发**，**对修改关闭。用抽象构建框架，用实现扩展细节。**（**面向抽象编程**）
>
> **优点：提高软件系统的可用性、可维护性。**

### 依赖倒置

> 解决问题：版本更新时，尽可能不修改源代码，以增加源代码的方式增加新功能。 
>
> 原则：**高层模块不应该依赖底层模块，二者都应该依赖其抽象；抽象不应该依赖细节，细节应该依赖抽象。（面向接口编程，不要针对实现编程）**
>
> **优点：减少类间耦合性、提高系统稳定性、提高代码可读性和可维护性，可降低修改程序所造成的的风险。**
>
> spring中**依赖注入**来实现依赖倒置。（通过接口注入）

### 单一职责原则

> 解决问题：若一个方法中处理了多个不同职责的东西，那么若一种职责有更变势必会影响到其他职责的东西，就可能引起一些风险。于是就引出了单一职责对此解耦。
>
> 原则：**一个类、接口、方法只负责一项职责。**
>
> **优点：降低类的复杂度、提供类的可读性、提供系统的可维护性、降低变更引起的风险。**

### 接口隔离原则

> **用多个专门的接口，而不是使用单一的总接口，客户端不应该依赖它不需要的接口。**
>
> **注意：一个类对应一个类的依赖应该建立在最小的接口上，建立单一接口，不用建立庞大臃肿的解耦；尽量细化接口，接口中的方法尽量少。**
>
> **遵循该原则时适度，一定要适度，也不能太细化。**
>
> **注意：在设计接口时多思考业务模型，包括以后可能发生变更的地方还要做一些预判。**
>
>  优点：增强可读性、后期可维护性。

### 迪米特法则

> **一个对象应该对其他对象保持最少的了解。又叫最小知道原则。目的是降低类与类之前的耦合。**
>
> **优点：降低类之间的耦合。**
>
> **强调只和朋友交流，不和陌生人说话。出现在成员变量、方法的输入输出参数中的类成为成员朋友类，而出现在方法体内部的类不属于朋友类。**



### 里氏替换原则

> 子类可扩展父类的功能，但是不能改变父类原有的功能。
>





### 合成复用原则

> 尽量使用对象组合(has-a)、聚合(contain-a)，而不是继承关系达到复用的目的。可以使系统根据灵活，降低类与类之间的耦合度，一个类发生变化对其他类的影响也较小。
>
> 继承：又叫做白箱复用，相当于把所有实现细节都暴露给子类。
>
> 组合/聚合：又叫做黑箱复用，对类以外的对象是无法获取到类的实现细节的。



## 设计模式

#### 简单工厂模式

**适用场景**

> 工厂类负责创建的对象较少。
>
> 客户端只需要传入工厂类的参数，对于如何创建对象的逻辑不需要关心。

**优点**

> 只需传入一个正确的参数就可获取需要的对象，无须知道其创建的细节。

**缺点**

> 职责相对过重，增加新的产品时需要修改工厂类的判断逻辑，违背开闭原则。不易于扩展过于复杂的产品结构。

**使用实例**

```java
// 定义水果接口
public interface IFruit {
    String producer();
}

// 具体实现类
public class Apple implements IFruit {
    @Override
    public String producer() {
        return "生产-苹果";
    }
}

// 生产水果的工厂类
public class FruitFactory {
    public IFruit create(Class<? extends IFruit> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
// 测试类
public class SimpleTest {
    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        IFruit apple = fruitFactory.create(Apple.class);
        System.out.println(apple.producer());
    }
}

```

**在源码中的应用实例**

```java
Calendar.getInstance();
LoggerFactory.getLogger(XXX.class); // slf4j-log4j12
```



#### 工厂方法模式

**解决问题**

> 随着产品链的丰富，若每种产品（水果）的创建逻辑有区别，那么工厂的职责就会越来越多，甚至有点像万能工厂，并不便于维护。那么根据单一职责原则将继续进行拆分，每一种产品（水果）由生产该产品的工厂创建，对工厂本身也做一个抽象。



> 简单工厂就像一个加工多种产品的工厂，
>
> 一个公司最开始不知道造什么产品能够买得很好，于是成立了多个小组分别造风扇、电灯、鼠标、键盘等等各种各样的产品。随着产品链的丰富不同的产品制作工艺差别太大，从整体进行管理时就没那么方便了。于是就成立了各个产品的制造分公司（工厂方法），各个分公司管理各自的产品线，总部管理各个分公司就行了，就不用管理到具体的生产线。



**适用场景**

> 创建对象需要大量重复的代码。
>
> 客户端（应用层）不依赖于产品类实例如何被创建、实现等细节。
>
> 一个类通过其子类来指定创建哪个对象。

**优点**

> 用户只需要关系所需产品对应的工厂，无需关系实现细节。

**缺点**

> 1. 类的个数容易过多，增加复杂度
> 2. 增加 了系统的抽象性和理解难度。

**使用实例**

```java
// 定义水果接口
public interface IFruit {
    String producer();
}

// 创建水果的工厂
public interface IFruitFactory {
    IFruit create(Class<? extends IFruit> clazz);
}

// 实现水果工厂的具体某种水果生产方式
public class AppleFactory implements IFruitFactory {
    @Override
    public IFruit create(Class<? extends IFruit> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// 具体生产（生产红苹果-或者青苹果等不同小类）
public class AppleRed implements IFruit {
    @Override
    public String producer() {
        return "生产-红苹果";
    }
}
public class AppleGreen implements IFruit {
    @Override
    public String producer() {
        return "生产-青苹果";
    }
}

// 测试类
public class SimpleTest {
    public static void main(String[] args) {
        // 实例化水果工厂中的苹果生产工厂
        IFruitFactory appleFactory = new AppleFactory();
        // 生产红苹果或青苹果
        IFruit redApple = appleFactory.create(AppleRed.class);
        IFruit greenApple = appleFactory.create(AppleGreen.class);
        System.out.println(redApple.producer());
        System.out.println(greenApple.producer());
    }
}

```





#### 抽象工厂模式

**适用场景**

> 提供一个创建**一系列相关或相互依赖对象**的接口，无须指定他们具体的类。提供一个产品类的库，所以的产品以同样的接口出现，从而是客户端不依赖于具体实现。

**缺点**

> 规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口。
>
> 整个类非常多，增加系统的抽象性和理解难度。

**优点**

> 具体产品在应用层代码隔离，无须关系创建细节。
>
> 将一个系列的产品族统一到一起创建。

> 产品族：一系列的相关产品，整合到一起有关系性。
>
> 产品等级：同一个继承体系。



简单工厂：产品的工厂

工厂方法：工厂的工厂

抽象工厂：复杂产品的工厂









