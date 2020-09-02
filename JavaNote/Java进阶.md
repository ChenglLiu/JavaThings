# Java进阶学习笔记

## Java基础09

### 多线程（Threads）

#### 1. 一些基础

+ 程序(program)：一组指令的集合，也就是一段静态的代码
+ 进程(process)：程序的一次执行过程，是资源分配的单位
+ 线程(thread)：是一个程序内部的一条执行路径，作为调度和执行的单位，拥有独立的运行栈和程序计数器(pc)

+ 单核CPU和多核CPU
  - 单核CPU，假的多线程
  - 多核CPU，更好的发挥多线程的效率
  - 一个Java应用程序的java.exe，至少有三个线程：main( )主线程、gc( )垃圾回收线程、异常处理线程。发生异常会影响主线程
+ 并行与并发
  - 并行，多个CPU同时处理多个任务
  - 并发，CPU采用时间片轮转同时执行多个任务



#### 2. 多线程的优点

> 即便是单核CPU下，多线程的效率更低，但仍有许多优点：

1. 提高应用程序的相应

2. 提高计算机系统CPU的利用率

3. 改善程序结构，将复杂的进程分为多个线程，独立运行

> 为何需要多线程？

+ 程序需要同时执行多个任务
+ 程序需要进行一些等待任务
+ 程序需要后台运行



#### 3. 多线程的创建和使用

> ``Thread类``的有关方法

+ ``void start()``：启动线程，执行对象的``run()``方法
+ ``run()``：线程在被调度时执行的操作
+ ``String getName()``：返回线程的名称
+ ``void setName(String name)``：设置该线程的名称
+ ``static Thread currentThread()``：返回当前线程。在Thread子类中就是this，通常用于主线程和Runnable实现类
+ ``yield()``：释放当前线程的CPU使用权
+ ``join()``：在线程A中调用线程B的``join()``方法，线程A进入阻塞状态，直到线程B完全执行完以后，线程A结束阻塞状态**（本身会报异常）**
+ ``stop()``：强行结束当前线程（不建议使用）
+ ``sleep(long millitime)``：让当前线程睡眠指定的millitime毫秒数，当前的线程为阻塞状态
+ ``isAlive()``：判断当前线程是否还存活



##### 1）方式一：继承``Thread``类

> ``Java``的``JVM``允许程序运行多个线程，通过``java.lang.Thread类``来实现

```java
public class ThreadTest {
    public static void main(String[] args) {
        //3. 创建子类对象
        MyThread mythread = new MyThread();
        
        //4. 调用start()，启动当前线程，调用当前线程的run()
        mythread.start();
        
        //创建Thread类的匿名子类
        new Thread() {
            @Override
            public void run() {
                /*线程-2
                do somethings*/
            }
        }.start();
        
        /*主线程
        do somethings*/
        try {
            if (...) {
                mythread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//1. 继承Thread类
class MyThread extends Thread {
    //2. 重写run()方法
    @Override
    public void run() {
        /*线程-1的操作
        do something;*/
       	
        if (...) {
        	//释放CPU
            yield();
        }
    }
}
```

> 1. 只能用``start()``启动线程，不能直接调用``run()``的方式启动线程，否则只是相当于顺序调用子类的方法
> 2. 不能再``start()``已经``start()``的线程，否则出现``IllegalThreadException``异常，但可以再``new``一个线程对象去``start()``



##### 2）方式二：实现``Runnable``接口

```java
public class ThreadTest02 {
    public static void main(String[] args) {
        //3. 创建实现类的对象
        MyThread myThread1 = new MyThread();

        //4. 将对象作为参数传递到Tread类的构造器中，创建Thread对象
        Thread thread1 = new Thread(myThread1);

        //5. 调用start()，①启动线程，②调用该线程的run()，调用了Runnable类型的target的run()
        thread1.start();
    }
}

//1. 创建一个实现了Runnable接口的类
class MyThread implements Runnable {
    //2. 实现类重写run()方法
    @Override
    public void run() {
        for (int i=0; i<=100; i++) {
            if (i%2 == 0) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}
```



##### 3）两种方式的对比

1. 均需要重写``run()``方法
2. ``Java``是单继承的，如果有显示继承的类，那么则不能用继承``Thread类``的方式创建多线程
3. 如果创建多线程有共享的数据，采用继承``Thread类``的方式需要采用``static``；而实现``Runnable接口``的方式天然共享其中的数据
4. ``Thread类``本身也实现了``Runnable接口``



#### 4. 线程的调度

+ 调度策略
  - 时间片轮转
  - 抢占式
+ Java的调度方法
  - 先来先服务(FCFS)
  - 优先权调度



##### 1）线程的优先级

+ 优先等级
  - MAX_PRIORITY: 10
  - NORM_PRIORITY:  5
  - MIN_PRIORITY:  1
+ 方法
  - ``getPriority()``：返回线程的优先值
  - ``setPriority(int p)``：改变线程的优先级
+ 说明
  - 线程创建时继承父线程的优先级
  - 低优先级线程只是获得**调度的概率低**，并不是一定在高优先级线程之后调度



#### 5. 线程的生命周期

+ JDK中用``Thread.State类``定义了几种线程状态
  - 新建：一个``Thread类``或其子类的对象被声明并创建时
  - 就绪：新建状态的线程被``start()``后，等待分配CPU资源时
  - 运行：就绪的线程获得CPU执行权，执行``run()``
  - 阻塞：特殊情况下，被挂起回执行IO操作时
  - 消亡：线程完成了全部工作，或提前被强制中止，或出现异常



#### 6. 线程安全问题

> 通过同步机制

##### 1）方式一：同步代码块

```java
//操作共享数据的代码
synchronized (同步监视器) {
    //需要被同步的代码
}
//同步监视器：锁。任何一个类的对象都可以当锁，且多个线程共用同一把锁

Object obj = new Object();
synchronized (obj) {
    //
}
```

+ 实现类：天然共享数据，最方便使用``this``对象
+ 继承Thread：需要将共享数据声明为``static``



##### 2）方式二：同步方法

> 1. 解决Runnable接口的线程安全

```java
//同步监视器，this 
public synchronized void method() {

}
```

> 2. 解决继承Thread类的线程安全

```java
//同步监视器，当前类
public static synchronized void method() {
    //共享代码
}
```



##### 3）线程死锁

+ 死锁
  - 两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去
  - 不是异常、不会有提示、只是处于阻塞态
+ 解决办法
  - 专门的算法
  - 减少同步资源
  - 避免嵌套同步



##### 4）方式三：Lock(锁)

+ JDK5.0开始，Java提供了线程同步机制——通过显示定义同步锁对象来实现同步，同步锁使用Lock对象充当
+ ``java.util.concurrent.locks.Lock接口``是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独占访问，每次只能有一个线程对Lock对象加锁
+ **``ReentrantLock类``**实现了Lock，它拥有与synchronized相同的并发性和内存语义

```java
class Window implements Runnable {
    private int ticeket = 100;
    
    //1. instantiate ReentrantLock object
    private ReentrantLock lock = new ReentrantLock();
    
    @Override
    public void run() {
        while (true) {
            try {
                //2. call lock() method
                lock.lock();
                
                if (ticket > 0) {
                    System.out.println(Thread.currentThread.getName() + ": sell ticket number is " ticket);
                    ticket--;
                } else {
                    break;
                }
            } finally {
                //3. call unlock() method
                lock.unlock();
            }
            
        }
    }
}
```



***面试题：***

***``synchronized`` 与  ``lock`` 的异同***

+ 均解决线程安全问题
+ ``synchronized机制``在执行完同步代码后自动释放同步监视器（锁）
+ ``lock``需要手动启动（``lock()``）和释放同步（``unlock()``）



##### 5）线程通信

1. ``wait()``

> 有异常
>
> 当前线程进入阻塞状态，并释放同步监视器

2. ``notify()``

> 唤醒被``wait``的另一个线程，如果存在多个阻塞的线程，则唤醒优先级高的那一个

2. ``notifyAll()``

> 唤醒所有被``wait``的线程

*说明：*

+ ***``wait()、notify()、notifyAll()``只能出现在同步代码块或者同步方法中，即``synchronized(obj){}``中***

+ ***调用者必须是同步代码块或者同步方法中的同步监视器，也就是说``synchronized(obj)``中的同步监视器obj，那么调用者也必须是obj，否则会出现``IllegleMonitorStateException``***
+ ***``wait()、notify()、notifyAll()``定义在``java.lang.Object类``中***



***面试题：***

***``sleep()`` 和 ``wait()`` 的异同***

1. 相同点：均可使当前线程进入阻塞状态
2. 不同点：
   + 声明的位置不同：``Thread类``中声明``sleep()``，``Object类``中声明``wait()``
   + 调用的范围不同：任何场景可以调用``sleep()``，``wait()``必须使用在同步代码块 或同步方法中
   + 是否释放同步监视器：如果两者都在同步代码块或同步方法中，``sleep()``不会释放锁，``wait()``会释放锁



#### 7. 新增线程创建方式

##### 1）方式三：实现``Callable接口``

> 与Runnable相比，Callable功能更强大

+ 相比run()方法，可以有返回值
+ 方法可以抛出异常
+ 支持泛型的返回值
+ 需要借助``FutureTask类``，比如获取返回结果

> ``Futher接口``

+ 可以对``Runnable、Callable``任务的执行结果进行取消、查询是否完成、获取结果等
+ **``FutureTask``是``Future接口``的唯一实现类**
+ ``FutureTask``同时实现了``Runnable、Future接口``，它既可以作为``Runnable``被线程执行，又可以作为``Future``得到``Callable``的返回值

```java
//1. 创建一个实现Callable的实现类
class ThreadA implements Callable {
    //2. 实现call()方法，将此线程需要执行的操作声明在call()中
    @Override
    public Object call() throws Exception {
        //可以返回int（被封装成Integer返回）、String等
        return null;
    }
}

public class ThreadTest {
    public static void main(String[] args) {
        //3. 创建Callable接口实现类的对象
        ThreadA threadA = new ThreadA();
        
        //4. 将此Callable接口实现类的对象作为参数传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask futureTask = new FutureTask(threadA);
        
        //5. 将FutureTask类的对象作为参数传递到Thread类的构造器中，创建Thread类的对象，并调用start()，启动线程
        new Thread(futureTask).start();
        
        //得到返回值
        try {
            //6. 获取Callable中的call方法返回值
            //get()返回值即为FutureTask构造器参数Callable实现类重写call()的返回值
            Object obj = futureTask.get();
            System.out.println(obj);
        } catch (InterrupedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```



##### 2）方式四：使用线程池

> **线程池相关API：``ExecuotorService``和``Executors``**

+ ``ExecutorService``：真正的线程池接口，常见子类``ThreadPoolExecutor``
  - ``void execute(Runnable command)``：执行任务/命令，没有返回值，一般用来执行Runnable
  - ``<T>Future<T>submit(Callable<T>task)``：执行任务，有返回值，一般用来执行Callable
  - ``void shutdown()``：关闭连接池
+ ``Executors``：工具类、线程池的工厂类，用于创建并返回不同类型的线程池
  - ``Executors.newCachedThreadPool()``：创建一个可根据需要创建新线程的线程池
  - ``Executors.newFixedThradPool(n)``：创建一个可重用固定线程数的线程池
  - ``Executors.newSingleThreadExecutor()``：创建一个只有一个线程的线程池
  - ``Executors.newScheduledThreadPool(n)``：创建一个线程池，它可安排在给定延迟后运行命令或定期地执行

```java
//1. 提供指定线程数的线程池
ExecutorService service = Executors.newFixedThreadPool(10);

//设置线程池的属性
ThreadPoolExecutor ser = (ThreadPoolExecutor)service;
ser.setCorePoolSize(15);
ser.setKeepAliveTime(10);

//2. 执行指定的线程的操作，需要提供一个实现Runnable或Callable接口的实现类的对象
service.execute(Runnable runnable);          //适用于Runnable
//        service.submit(Callable callable);           //适用于Callable

//3. 关闭连接池
service.shutdown();
```



## Java基础10

### String类

#### 1. 特性

+ 字符串
+ ``String``是一个``final类``，代表**不可变**的字符序列，不能被继承
  - 当对字符串重新赋值时，需重写指定内存区域赋值，不能在原有的value数组进行赋值
  - 进行拼接操作时，也需重新指定内存区域赋值
  - 调用``replace()``方法时，也要重新指定内存区域赋值赋给新变量
+ ``String``对象的字符内容是存储在一个字符数组``value[]``中的
+ 实现了``Serializable接口``和``Comparable接口``，表示支持序列化和可比较大小



#### 2. 创建

> ``String``的实例化方式

```java
//字面量赋值，str1和str2的数据javaEE声明在方法区的字符串常量池中
String s1 = "javaEE";
String s2 = "javaEE";

//new + 构造器，str3和str4保存的地址值，在堆空间保存数据的地址值
//创建了两个对象，一个是堆空间new的结构，另一个是char[]数组对应的常量池中的数据"javaEE"
String s3 = new String("javaEE");
String s4 = new String("javaEE");

String s5 = s3.intren();			//intern()的返回值在常量池中

System.out.println(s1 == s2);		//true
System.out.println(s1 == s3);		//false
System.out.println(s3 == s4);		//false
System.out.println(s2 == s5);   	//true
```



#### 3. 方法

##### 1）String--->包装类

```java
int num = Integer.parseInt(str);
String str = String.valueOf(num);
```

##### 2）String--->char[]

```java
char[] charArray = str.toCharArray();
String str = new String(charArray);
```

##### 3）String--->byte[]

```java
//默认编码方式
byte[] bytes = str.getBytes();
//默认解码方式
String str = new String(bytes);
```

```java
//指定gbk字符集进行编码
byte[] gbks = str.getBytes("gbk");
//乱码，编码和解码方式不一致
String str = new String(gbks);
//指定gbk字符集进行解码
String str = new String(gbks, "gbk");
```



### ``StringBuffer类``和``StringBulider类``

> 均是可变的字符序列

#### 1. StringBuffer

> 线程安全的，效率较低

#### 2. StringBuilder

> 线程不安全，效率高



### 日期时间API

#### 1. JDK8之前

##### 1）时间戳

> 返回当前时间与1970年1月1日0时0分0秒以毫秒记的时间差

``long time = System.currentTimeMillis()``



##### 2）Date类

> ``java.util.Date``

+ 构造器的使用

  ```java
  import java.util.Date;
  
  //创建当前时间的Date对象
  Date date1 = new Date();
  //显示当前的年、月、日、时、分、秒
  System.out.println(date1.toString());
  //时间戳
  System.out.println(date.getTime());
  
  //创建指定毫秒数的Date对象
  Date date2 = new Date(1550306204104L);
  
  //java.util.Date的子类java.sql.Date
  java.sql.Date date3 = new java.sql.Date(550306204104L);
  
  //java.util.Date--->java.sql.Date
  Date date3 = new Date();
  java.sql.Date date4 = new java.sql.Date(date6.getTime());
  ```



##### 3）``java.text.SimpleDateFormat类``

> 对日期Date类的格式化和解析

```java
//实例化，默认构造器
SimpleDateFormat sdf = new SimpleDateFormat();

//格式化：日期--->字符串
Date date = new Date();
String format = sdf.format(date);

//解析：字符串--->日期
String str = "19-2-18 上午9:00";
Date date = sdf.parse(str);			//会抛异常

//实例化，指定格式
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
System.out.println(sdf.format(date));
```



##### 4）java.util.Calendar

> 抽象类

```java
//实例化，1. 创建子类对象GregorianCalendar的对象
//实例化，2. 调用其静态方法getInstance()
Calendar calendar = Calendar.getInstance();
System.out.println(calendar.getClass());			//GregorianCalendar

//常用方法
//get()
int days = calendar.get(Calendar.DAY_OF_MONTH);

//set()
calendar.set(Calendar.DAY_OF_MONTH, 22);

//add()
calendar.add(Calendar.DAY_OF_MONTH, 3);

//getTime()：日历类--->Date
Date date = calendar.getTime();

//setTime()：Date--->日历类
Date date = new Date();
//具有可变性
calendar.setTime(date);
```



#### 2. JDK8的新日期API

##### 1）LocalDate、LocalTime、LocalDateTime

```java
//实例化：now()：获取当前的日期、时间、日期时间
LocalDate localDate = LocalDate.now();
LocalTime localTime = LocalTime.now();
LocalDateTime localDateTime = LocalDateTime.now();

//of()：设置指定的年月、日、时、分、秒
LocalDateTime localDateTime1 = LocalDateTime.of(2019, 10, 1, 9, 40, 0);

//getXxx()
System.out.println(localDateTime.getDayOfMonth());

//withXxx()：设置相关属性
//不可变性
```



##### 2）Instant：瞬时

```java
//实例化：now()：本初子午线标准时间
Instant instant = Instant.now();
//添加时间的偏移量
OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
System.out.println(offsetDateTime);

//获取时间戳
long milli = instant.toEpochMilli();
//通过给定的毫秒数获取Instant实例
Instant instant1 = Instant.ofEpochMilli(159653513587L);
```



##### 3）``java.time.format.DateTimeFormatter``

> 格式化或者解析时间

```java
//预定义
DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//格式化：日期--->字符串
LocalDateTime localDateTime = LocalDateTime.now();
String str1 = formatter.format(localDateTime);

//解析：字符串-->日期
TemporalAccessor parse = formatter.parse("2019-10-01T9:40:20.797");
```

```java
//本地化相关的格式
DateTimeFormatter formatter = DateTimeFormatter.ofLocalizeDateTime(FormatStyle.SHORT);
//格式化
String str2 = formatter.format(localDateTime);
```

```java
//自定义格式：如ofPatten("yyyy-MM-dd hh:mm:ss")
DateTimeFormatter formatter = DateTimeFormatter.ofPatten("yyyy-MM-dd hh:mm:ss");
//格式化
String str3 = formatter.format(LocalDateTime.now());
//解析
TemporalAccessor accessor = formatter.parse("2019-10-01");
```



### Java比较器

> 正常情况下，Java中只能比较``==`` 或`` !=``，不能使用``>`` 、``< ``；如何比较两个对象的大小，使用两个接口：``Comparable``或``Comparator``

#### 1. Comparable

> 自然排序

+ String、包装类实现了Comparable接口，重写了compareTo()方法，比较两个对象大小

+ 重写``compareTo()``的规则
  - 当前对象this大于形参对象obj，返回正整数
  - 当前对象this小于形参对象obj，返回负整数
  - 当前对象this等于形参对象obj，返回零

+ 自定义类实现Comparable接口，重写``compareTo(obj)``，在``compareTo(obj)``方法中指明如何排序

```java
class Goods implements Comparable {
    private String name;
    private int value;
    private String str;
    
    //getter() & setter()
    //...
    
    @Override
    public int compareTo(Objcet o) {
        if (o instanceof Goods) {
            if (this.getName().equals(o.getName())) {
                if (this.getValue() > o.getVaule()) {
                    return 1;
                } else if (this.getValue() < o.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                return this.getStr().compareTo(o.getStr());
            }
        }
        throw new RuntimeException("...");
    }
}
```



#### 2. Comparator

> 定制排序

```java
class Goods {
    private int value;
    private String str;
}

Goods[] arr = new Goods[5];
Arrays.sort(arr, new Comparator() {
   @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Goods && o2 instanceof Goods) {
            Goods g1 = (Goods)o1;
            Goods g2 = (Goods)o2;
            if (g1.getValue() == g2.getValue()) {
                return g1.getStr().compareTo(g2.getStr());
            } else {
                return -Integer.compare(g1.getValue(), g2.getValue());
            }
        }
        throw new RuntimeException("...");
    } 
});

System.out.println(Arrays.toString(arr));
```



### System类

> + java.lang包
> + 构造器是private的，无法实例化，成员变量和成员方法都是static的
> + 成员变量
>   - in
>   - out
>   - err
> + 成员方法
>   - ``native long currentTimeMillis()``
>   - ``void exit(int status)``
>   - ``void gc()``：请求系统进行垃圾回收
>   - ``String getProperty(String key)``：获取系统中属性名为key的属性对应的值



### Math类



### ``BigInteger``与``BigDecimal``



## Java基础11

### 枚举类

#### 1. JDK5.0以前

> + 类的对象是有限个的，确定的
> + 需要定义一组常量时，建议使用枚举类
> + 如果枚举类只有一个对象，则作为单例模式的实现方式

```java
class Season {
    //1. 声明属性是private final
    private final String Name;
    private final String Desc;
    //2. 私有化构造器，并给属性赋值
    private Season(String Name, String Desc) {
        this.Name = Name;
        this.Desc = Desc;
    }
    //3. 提供枚举类对象，声明为public static final
    public static final Season SPRING = new Season("春天", "百花齐放");
    public static final Season SUMMER = new Season("夏天", "映日荷花");
    public static final Season AUTUMN = new Season("秋天", "金色大地");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");
    //4. 其他诉求，获取枚举类对象的属性
    public String getName() {
        return Name;
    }
    public String getDesc() {
        return Desc;
    }
    @Override
    public String toString() {
        return "Season{Name: " + Name + ", Desc: " + Desc + "}";
    }
}
```



#### 2. 枚举类的实现

> + 用enum关键字定义枚举类
>
> + 默认继承于``java.lang.Enum``类

```java
enum Season {
    //1. 一开始就需要提供枚举类对象，每个对象之间用逗号隔开
    SPRING("春天", "百花齐放"),
    SUMMER("夏天", "映日荷花"),
    AUTUMN("秋天", "金色大地"),
    Season("冬天", "白雪皑皑");
    
    //2. 声明属性是private final
    private final String Name;
    private final String Desc;

    //3. 私有化构造器，并给属性赋值
    private Season(String Name, String Desc) {
        this.Name = Name;
        this.Desc = Desc;
    }

    //4. 其他诉求，获取枚举类对象的属性，不必重写toString()
    public String getName() {
        return Name;
    }
    public String getDesc() {
        return Desc;
    }
}

System.out.println(Season.AUTUMN);			//output: AUTUMN
```



#### 3. Enum的主要方法

+ ``values()``：返回枚举类型的对象数组，该方法可以遍历枚举值
+ ``valueOf(String str)``：把一个字符串转为对应的枚举对象，要求字符必须是枚举类型对象
+ ``toString()``：返回当前枚举类对象常量的名称



#### 4. 实现接口

> + 实现接口，重写接口的方法
> + 让枚举类的对象都去实现抽象方法

```java
interface Info {
	void show();			//public abstract
}
//重写接口方法
enum Season implements Info {
    SPRING("春天");
    private final String name;
    private Season(name) {
        this.name = name;
    }
    @Override
    public void show() {
        System.out.println("...");
    }
}

//每个枚举类对象都去重写接口方法
enum Season implements Info {
    SPRING("春天") {
        @Override
        public void show() {
            System.out.println("---");
        }
    },
    WINTER("冬天") {
        @Override
        public void show() {
            System.out.println("+++");
        }
    };
    private final String name;
    private Season(name) {
        this.name = name;
    }
}
```



### 注解(Annotation)

> 代码里的特殊标记，可以在编译、类加载、运行时被读取。并执行相应的处理

#### 1. Annotation使用示例 

+ 生成文档相关的注解
+ 在编译时进行格式检查（JDK内置的三个基本注解）
  - Override：限定重写父类方法，只用于方法
  - Deprecated：用于表示所修饰的类、方法已经过时
  - SuppressWarnnings：抑制编译器警告
+ 跟踪代码依赖性，实现替代配置文件功能



#### 2. 自定义注解

> 参照SuppressWarnnings定义

+ 关键字：@interface
+ 自动继承了``java.lang.annotation.Annotation``接口
+ Annotation的**成员变量**在Annotation定义中以无参数方法的形式来声明
+ 可以在定义成员变量时为其指定初始值，可使用default关键字
+ 如果只有一个参数成员，建议使用参数名为value
+ 若注解含有配置参数，格式为“参数名 = 参数值”，若只有一个参数且名为value，则可以省略“value = ”



#### 3. JDK的元注解

+ Retetion：指定所修饰的Annotation的生命周期
+ Target：用于指定被修饰的Annotation能用于修饰哪些程序元素
+ Documented：所修饰的注解在被javadoc解析时，保留下来
+ Inherited：被它修饰的Annotation将具有继承性



## Java基础12

### Java集合

#### 1. 集合框架的概述

+ 集合、数组是对多个数据进行存储的结构，简称Java容器

+ 数组的缺点：长度和数组类型确定

+ Java集合可分为Collection和Map两种体系
  - **Collection接口**：单列数据，定义了存取一组**对象**的方法的集合
    - List：元素有序，可重复的集合，“动态数组”：``ArrayList、LinkedList、Vector``
    - Set：元素无序，不可重复的集合：``HashSet、LinkedHashSet、TreeSet``
  - **Map接口**：双列数据，保存**映射关系**的集合：``HashMap、LinkedHashMap、TreeMap、Hashtable、Properties``



#### 2. Collection

##### 1）Collection接口

```java
Collection coll = new ArrayList();
//1. add(Object o)
coll.add(123);		//自动装箱
coll.add(new String("Torking"));
//2. size()
System.out.println(coll.size());		//2
//3. addAll()
Collection c = new ArrayList();
c.add(("Plus");
c.addAll(c);
System.out.println(c.size());			//3
//4. isEmpty()
//5. clear()

coll.add(new Person("Plus", 23));
//6. contains()：判断是否在集合中出现过，会调用obj对象所在类的equals()，最好重写equals()
System.out.println(coll.contains(new String("Torking")));		//true
System.out.println(coll.contains(new Person("Plus", 23)));		//false
//7. containsAll(Collection coll)：判断形参coll中的所有元素是否都存在于当前集合中
Collection c = Arrays.asList(123, 456);
boolean flag = coll.contains(c);
      
//8. remove():删除自定义类时，也要重写equals()才能成功
      
//9. removeAll(Collection coll)：从当前集合中移除coll中的元素 
      
//10. retainAll(Collection coll)：获取当前集合与coll的交集
coll.retainAll(c);
      
//11. equals(Object obj)：比较元素是否相等      
```

12. hashCode()：返回当前对象的哈希值
13. 集合--->数组：toArray()

``object[] obj = coll.toArray();``

14. 数组--->集合：调用Arrays类的静态方法asList()

```java
List<String> list = Arrays.asList(new String[]{"aa", "bb", "cc"});
```

15. iterator()：返回Iterator接口的实例，用于遍历集合元素

> 内部方法：``hasNext()`` 和 ``next()``

```java
import java.util.Iterator;

Collection c = new ArrayList();

Iterator.iterator = c.iterator();
//1. recommend
while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
//2. 
for (int i=0; i<c.size(); i++) {
    System.out.println(iterator.next());
}

//3. foreach: for(Object obj : collection)
//内部仍然用的迭代器
for (Object obj : c) {
    System.out.println(c);
}
```

> remove()：在未调用next()或调next()之前已经调用了remove()，再调用remove()会报IllegalStateException

``iterator.remove()``



##### 2）List接口

> 存储有序的、可重复的数据

+ ``ArrayList``：作为List接口的主要实现类，线程不安全，效率高，底层使用``Object[]``
+ ``LinkedList``：底层用双向链表存储
+ ``Vector``：作为List接口的古老实现类，线程安全，效率低，底层使用``Object[]``



##### 3）Set接口

> 存储无序的、不可重复的数据

> 向Set中添加的数据，其所在的类一定要重写``hashCode()``和``equals()``，并且重写尽可能保持一直，相等的对象拥有相同的散列码

+ ``HashSet``：作为Set接口的主要实现类，线程不安全的，可以存储null值

  > 1. 无序性，不是随机性，存储的数据在底层数组中不是按照数组索引的顺序添加，而是根据数组的**哈希值**
  >
  > 2. 不可重复性：保证添加的元素按照``equals()``判断时，不返回true，即相同的元素只能添加一个

  + ``LinkedHashSet``：作为HashSet的子类，遍历其内部数据时，可以按照添加的顺序遍历

+ 重写``hashCode()``方法的基本原则

  - 程序运行时，同一个对象多次调用``hashCode()``方法应返回相同的值
  - 当两个对象的``equals()``方法比较true时，两个对象的``hashCode()``方法的返回值也应相等
  - 对象中用作``equals()``反法比较的Filed，都应用来计算hashCode值

+ ``TreeSet``：按照添加**对象**的指定属性，进行**排序**

  - 底层：红黑树
  - 向``TreeSet``中添加的对象，必须要求时同类对象，否则抛出``ClassCastException``
  - 两种排序方式：自然排序（实现``Comparable接口``）和定制排序（``CompareTo``）



#### 3. Map

##### 1）HashMap

> 底层：数组+链表+红黑树（jdk8引入的红黑树）
>
> 作为Map的主要实现类，线程不安全，效率高；可存储**``null``**的``key``或者``value``

+ LinkedHashMap：按照添加的顺序实现遍历，在原有的底层结构上，添加了指向前后的引用

> 元视图操作：

```java
Map map = new HashMap();
map.put("AA", 233);
map.put(123, 777);

//1. 遍历所有地key集合
Set set = map.keySet();
Iterator iterator = set.iterator();
while (iterator.hasNext()) {
    System.out.println(iterator.next());
}

//2. 遍历所有的value集合
//map.values()返回的是一个Collection的实现类的对象
Collection values = map.values();
for (Object obj : values) {
    System.out.println(obj);
}

//遍历所有的key-value对
//方式一：entrySet()
Set entrySet = map.entrySet();
iterator = entrySet.iterator();
while (iterator.hasNext()) {
    Object object = iterator.next();
    //entrySet集合中的元素都是entry，强转
    Map.Entry entry = (Map.Entry)object;
    System.out.println(entry.getKey() + "--->" + entry.getValue() );
}
//方式二：
Set keySet = map.keySet();
iterator = keySet.iterator();
while (iterator.hasNext()) {
    Object key = iterator.next();
    Object value = map.get(key);
    System.out.println(key + "--->" + value);
}
```



##### 2）TreeMap

> 底层是红黑树
>
> 按照添加的**key**-value进行排序，实现排序遍历，考虑key的**自然排序**或者**定制排序**

```java
//定制排序
//排序方法
TreeMap treeMap = new TreeMap(new Comparator() {
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Person && o2 instanceof Person) {
            Person p1 = (Person)o1;
            Person p2 = (Person)o2;
            return Integer.compare(p1.getAge(), p2.getAge());
        }
        throw new RuntimeException("类型不匹配...");
    }
});

//自然排序
//对Person类内部排序就行
@Override
public int compareTo(Object o) {
    if (o instanceof Person) {
        Person person = (Person)o;

        if (this.name.equals(person.name)) {
            return -Integer.compare(this.age, person.age);
        } else {
            return this.name.compareTo(person.name);
        }

        //            return this.name.compareTo(person.name);
    } else {
        throw new RuntimeException("输入类型不匹配...");
    }
}
```



##### 3）Hashtable

> 线程安全，效率低；不能存储**``null``**的``key``或者``value``

+ Properties：处理配置文件，``key``和``value``都是``String``类型

```java
ileInputStream fis = null;
try {
    Properties pros = new Properties();

    fis = new FileInputStream("jdbc.properties");
    pros.load(fis);     //加载流对应的文件
    String name = pros.getProperty("name");
    String password = pros.getProperty("password");

    System.out.println("name = " + name + ", password = " + password);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (fis != null) {
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```



####  4. Collections工具类

```java
//反转
void reverse(List)
Collections.reverse(list);

//随机排序
Collections.shuffer(list);
//排序
sort(List);
sort(List, comparator);
Collections.sort(list);

//交换指定位置的元素
Collections.swap(list, int i, int j);

//最大最小
Object min(Collection);
Object max(Collection);

//返回指定集合中指定元素的出现次数
int frequency(Collection, Object);

//将src中的内容复制到dest中
void copy(List dest, List src);
...
List list = new ArrayList();
list.add("...");
List dest = Arrays.asList(new Object[list.size()]);
dest.copy(list);
...

//使用新值替换List对象的旧值
boolean replaceAll(List list, Object oldVal, Object newVal);
```

```java
//返回线程安全的list
List newlist = Collections.synchronizedList(list);
```



### 泛型（Generic）

> 允许在定义类、接口的时候通过一个标识符表示类中某个属性的类型或者时某个方法的返回值及参数类型

> 泛型的类型必须是类，基本类型需要转换为包装类
>
> 如果没有指明泛型的类型，默认类型为``java.lang.Object``

#### 1. 在集合中使用泛型

```java
//集合中使用泛型
ArrayList<Integer> list = new ArrayList<Integer>();
list.add(93);
//编译错误
list.add("Tom");
//方式一：
for (Integer integer : list) {
    //避免了强转
    int score = integer;
    System.out.println(score);
}
//方式二：
Iterator<Integer> iterator = list.iterator();
while (iterator.hasNext()) {
    int score = iterator.next();
    System.out.println(score);
}

//以HashMap为例
Map<String, Integer> map = new HashMap<String, Integer>();
map.put("Mike", 92);
//泛型的嵌套
Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
while (iterator.hasNext()) {
    Map.Entry<String, Integer> entry = iterator.next();
    System.out.println(entry.getKey() + "--->" + entry.getValue());
}

/*
jdk 7.0以后，自动推断
Map<String, Integer> map = new Map<>();
*/
```



#### 2. 自定义泛型结构

##### 1）泛型类

> 如果定义的泛型类，实例化没有指明类的类型，则默认此泛型类为Object类型

```java
@Test
public void test() {
    subOrder01 sub01 = new subOrder01();
    sub01.setvalueT(124);				//Integer
    
    subOrder02<String> sub02 = new subOrder02<>();
    sub02.setvalueT("PlusTorking");
}

public class Order<T> {
    //类的内部结构可以使用类的泛型
    private T valueT;
    
    public Order() {}
   	public Order(T valueT) {
        this.valueT = valueT;
    }
    
    public void setValueT(T valueT) {
        this.valueT = valueT;
    }
}

//两种继承方式
//方式一：指明继承类型，则子类不再是泛型
public class subOrder01 extends Order<Integer> {
    
}
//方式二：子类依旧是泛型类
public class subOrder02 extends Order {
    
}
```



##### 2）泛型接口

##### 3）泛型方法

> 在方法中出现了泛型的结构，泛型参数与类的泛型参数没有任何关系
>
> 泛型方法所属的类是不是泛型类都没有任何联系

```java
public <E> List<E> copyFromArrayToList(E[] arr) {
    ArrayList list = new ArrayList<>();
    for (E e : arr) {
        list.add(e);
    }
    return list;
}

//静态泛型方法
//原因：泛型参数是在调用方法时才确定的，而非实例化类时确定
public static <E> List copyFromArrayToList(E[] arr) {
    
}
```



> ***补充：***

##### 继承关系

+ 类A是类B的父类或者类B是接口A的实现类，但是G<A> 和 G<B>二者不具备父子类关系，而是并列关系

+ A<T> 是 B<T>的父类

```java
List<Object> list1 = null;
List<String> list2 = new ArrayList<String>();
//编译不通过
list1 = list2;

List<String> list3 = null;
ArrayList<String> list4 = null;
list3 = list4;
//------>
List<String> list5 = new ArrayList<String>();
```



##### 通配符

> ?

```java
List<Object> list1 = null;
List<String> list2 = null;

List<?> list = null;

//list1 和 list2是没有关系的，二者的共同父类是 List<?>
list = list1;		//ok
list = list2;		//ok

//可以读，只能写null
list.add(null);
print(list1);
print(list2);

public void print(List<?> list) {
    Iterator<?> iterator = list.iterator();
    while (iterator.hasNext()) {
        Object obj = iterator.next();
        System.out.println(obj);
    }
}
```

>  具有限制条件的通配符使用

```java
? extends Person:
	G<? extends A> 可以作为G<A> 和 G<B>的父类，其中B是A的子类
? super Person:
	G<? super A> 可以作为G<A> 和 G<B>的父类，其中B是A的父类

```



## Java基础13

### File类

#### 1）File类的使用

> File类的一个对象，代表一个文件或一个文件目录（文件夹）
>
> 声明在``java.io``包下

```java
File(String filePath);
File(String parentPath, String childPath);
File(File parentPath, String childPath);
```

> 现在理解为内存层面的一个对象

```java
//ctor-1
File file1 = new File("hello.txt");             //相对路径，相对于当前module
File file2 = new File("D:\\Code\\git_Code\\HH_STUDY\\JavaThings \\JavaSenior\\src\\IOStream\\FileClass\\Aloha.txt");
System.out.println(file1);
System.out.println(file2);

//ctor-2
File file3 = new File("D:\\Code\\git_Code", "HH_STUDY\\JavaThings");
System.out.println(file3);

//ctor-3
File file4 = new File(file3, "Aloha.txt");
System.out.println(file4);
```



#### 2）常用方法

> 现在理解为内存层面的一个对象

```java
public String getAbsolutePath();	//绝对路径
public String getPath();			//路径
public String getName();			//名字
public String getParent();		//获取上层文件目录路径，若无，返回null
public long Length();			//获取文件长度（字节数）
public long lastModified();		//最近一次修改时间（毫秒值）

public String[] list();		//指定目录下所有文件或文件目录的名称数组
public File[] listFiles();	//指定目录下所有文件或文件目录的File数组（路径）

public boolean renameTo(File dest)	//把文件重命名为指定的文件路径
//true: file1 must be exist and file is not exist
//执行了之后再执行便是false
boolean renameTo = file1.renameTo(file2);

public boolean isDirectory();		//是否是文件目录
public boolean isFile();			//是否是文件
public boolean exists();			//是否存在
public boolean canRead();			//是否可读
public boolean canWrite();			//是否可写
public boolean isHidden();			//是否隐藏
```



> 创建硬盘中对应的文件和文件目录

```java
public boolean createNewFile();		//创建文件，若存在，返回false
public boolean mkdir();		//创建文件目录，若此文件目录存在，不创建；如果此文件目录的上级目录不存在，也不创建
public boolean mkdirs();	//创建文件目录，若上层文件目录不存在，则一并创建
public boolean delete();	//删除文件或文件夹
```



### IO流

#### 1）分类

+ 按数据单位：字节流、字符流
+ 按数据流向：输入流、输出流
+ 按角色：节点流、处理刘



#### 2）流的体系结构

| 抽象基类     | 节点流\文件流    | 缓冲流(处理流)       |
| ------------ | ---------------- | -------------------- |
| InputStream  | FileInputStream  | BufferedInputStream  |
| OutputStream | FileOutputStream | BufferedOutputStream |
| Reader       | FileReader       | BufferedReader       |
| Writer       | FileWriter       | BufferedWriter       |



#### 3）节点流\文件流

+ 读入（Reader）

```java
FileReader fs = null;
try {
    //1. 实例化File类的对象，指明操作的文件
    File file = new File("Aloha.txt");
    //file.createNewFile();
    //2. 提供具体的流
    fr = new FileReader(file);
    //3. 数据读入
    int data;
    while ((data = fr.read()) != -1) {
        System.out.println("2222");
        System.out.println((char) data);
    }
    /** 一次读入多个字符
    //read(char[] chbf)
    char[] chbf = new char[5];
    int len;
    while ((len = fr.read(chbf)) != -1) {
    	//输出方式一：
    	for (int i=0; i<len; i++) {
    		System.out.println(chbf[i]);
    	}
    	//输出方式二：
    	String str = new String(chbf, 0, len);
    	System.out.println(str);
    }
    */
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        //4. 关闭流
        if (fs != null) {
            fs.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```



+ 写出（Writer）

> 输出时，File文件可以不存在，当文件不存在时，写出操作会自动创建该文件；当文件存在时，可以选择在**文件末尾添加``new FileWriter(file, true)``**或者**覆盖该文件``new FileWriter(file, false)`` ``new FileWriter(file)``**

```java
//读入 & 写出
FileReader fr = null;
FileWriter fw = null;

try {
    //1.创建文件对象
    File file1 = new File("Aloha.txt");
    File file2 = new File("Hello.txt");
    //2.创建流对象
    fr = new FileReader(file1);
    fw = new FileWriter(file2);
    //3.数据读入和写出操作
    char[] chb = new char[5];
    int len;
    while ((len = fr.read(chb)) != -1) {
        //每次写出len个字符
        fw.write(chb, 0, len);
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    //4.关闭流
    //写法一：
    try {
        if (fw != null) fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (fr != null) fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //方式二：并列处理
    try {
        
    } catch( ) {
        
    }
    
    try {
        
    } catch () {
        
    }
}
```



+ 读入（FileInputStream）同（Reader）类似
+ 写出（FileOutputStream）同（Writer）类似

> 1. 对于文本文件(.txt .java .c .cpp)，使用字符流处理；非文本文件，用字节流处理
> 2. 使用字节流处理文本文件，**可能**出现乱码（汉字）
> 3. 对于复制，使用字节流即可

```java
//指定文件的复制
public void copy(String srcPath, String destPath) {
    FileInputStream fis = null;
    FileOutputStream fos = null;

    try {
        //1.创建指定操作的文件对象
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        //2.创建流对象
        fis = new FileInputStream(srcFile);
        fos = new FileOutputStream(destFile);

        //3.复制
        byte[] bf = new byte[7];
        int len;
        while ((len = fis.read(bf)) != -1) {
            fos.write(bf, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //4.关闭流对象
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```



#### 4）处理流之缓冲流

+ 缓冲流

``BufferedReader(read(char[] chbf) / readLine())``

```java
//实现文件的复制
public void copyFile(String srcPath, String destPath) {
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
        //1.造文件对象
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        //2.造节点流
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        //3.造缓冲流
        bis = new BufferedInputStream(fis);
        bos = new BufferedOutputStream(fos);

        //4.复制
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            //bos.flush();		//刷新缓冲区
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //关闭流
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bos != null) {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

>  ***说明：***
>
> 关闭外层流的时候，内层流会自动关闭



+ 作用：提供流的读取、写入速度



#### 5）处理流之转换流

+ 转换流：属于字符流

  ``InputStreamReader``：将一个**字节**的输入流转换为**字符**的输入流

  ``OutputStreamWriter``：将一个**字符**的输出流转换为**字节**的输出流

```java
File file1 = new File("Aloha.txt");
File file2 = new File("Aloha_gbk.txt");

InputStreamReader isr = null;
OutputStreamWriter osw = null;

try {
    FileInputStream fis = new FileInputStream(file1);
    FileOutputStream fos = new FileOutputStream(file2);

    isr = new InputStreamReader(fis, "utf-8");
    osw = new OutputStreamWriter(fos, "gbk");

    char[] chbf = new char[10];
    int len;
    while ((len = isr.read(chbf)) != -1) {
        String str = new String(chbf, 0, len);
        osw.write(str, 0, len);
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (isr != null) {
        try {
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (osw != null) {
        try {
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```



+ 作用：提供字节流与字符流之间的转换

+ 解码：字节、字节数组 ``--->`` 字符数组、字符串

  编码：字符数组、字符串 ``--->`` 字节、字节数组

+ 字符集

  ASCII

  ISO08859-1

  GB2312

  GBK

  Unicode

  UTF-8



#### 6）标准输入输出流

``System.in``：标准输入流，默认从键盘输入

``System.out``：标准输出流，默认从控制台输出

``System`` 类的 ``setIn(InputStream is) / setOut(PrintStream ps)`` 方式从指定输入和输出的流 

```java
//打印流
PrintStream ps = null;
try {
 	FileOutputStream fos = new FileOutStream(new File("D:\\IO\\test.txt"));
    //创建打印输出流，设置为自动刷新模式（写入换行符会刷新缓冲区
    ps = new PrintStream(fos, true);
    if (ps != null) {		//把标准输出流（控制台）改成文件
        System.setOut(ps);
    }
    
    for (int i=0; i<255; i++) {
        System.out.print((char)i);		//输出ASCII码
        if (i % 50 == 0) System.out.println();
    }
    
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (ps != null) {
        ps.close();
    }
}
```



#### 7）RandomAccessFile类

> 1. 声明在``java.io``下，直接继承于``java.lang.Object``，实现了``DataInput``和``DataOutput``接口
>
> 2. 既可以作为输入流，也可以作为输出流
> 3. 如果RandomAccessFile作为输出流，写出到的文件不存在，则在执行过程中自动创建文件；如果写出到的文件存在，则会对原有文件内容的覆盖（默认从头开始覆盖）

+ 构造器

  - ``public RandomAccessFile(File file, String mode)``

  - ``public RandomAccessFile(String name, String mode)``

+ mode参数指定RandomAccessFile的访问模式

  - r：只读。读取一个存在的文件，如果不存在，出现异常
  - rw：读取和写入。如果文件不存在，会去创建文件
  - rwd：读取和写入，同步文件内容的跟新
  - rws：读取和写入，同步文件内容和元数据的更新

+ ```java
  RandomAccessFile raf = new RandomAccessFile(new File("test.txt"));
  raf.seek(3);		//将指针调到角标为3的位置
  
  //保存指针后的数据
  StringBuilder builder = new StringBuilder((int)new File("test.txt").length());
  byte[] buffer = new byte[20];
  int len;
  while ((len = raf.write(buffer)) != -1) {
      builder.append(new String(buffer, 0, len));
  }
  raf.seek(3);
  raf.write("abc", getBytes());		//write():覆盖
  raf.write(builder.toString().getBytes());
  
  raf.close();
  ```





### 对象序列化

> 类需要满足以下要求，方可序列化
>
> 1. 需要实现接口：Serializable
> 2. 当前类需要提供一个全局常量：serialVersionID（同自定义异常类）
> 3. 需要保证类内部所有的属性也必须是可序列化的（默认情况下，基本数据类型是可序列化的）



### 网络编程

#### 1）通信要素：IP和端口号

> 1. 在Java中用InetAddress类表示IP
>
> 2. 本地回路地址，127.0.0.1，对应localhost

```java
InetAddress inet = InetAddress.getByName("192.168.10.14");
InetAddress inet = InetAddress.getByName("www.baidu.com");
//获取本机地址
InetAddress inet = InetAddress.getLocalHost();

InetAddress inet = InetAddress.getHostName();
InetAddress inet = InetAddress.getHostAddress();
```

+ 端口号标记正在计算机上执行的进程（程序）

> 1. 不同进程有不同的端口号
> 2. 规定为一个16位的整数

+ socket：网络套接字，端口号与IP地址组合



#### 2）TCP网络编程

```java
//客户端
@Test
public void client() {
    OutputStream os = null;
    Socket socket = null;
    FileInputStream fis = null;
    InputStream is = null;
    ByteArrayOutputStream baos = null;
    try {
        //1.创建Socket对象，指明服务器端的ip地址和端口号
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
        socket = new Socket(inetAddress, 8989);

        //2.获取一个输出流，用于输出数据
        os = socket.getOutputStream();
        
        //3.获取文件流
        fis = new FileInputStream(new File("txet.jpg"));
        
        //4.写出数据
        byte[] buffer1 = new byte[7];
        int len1;
        while ((len1 = fis.read(buffer1)) != -1) {		//read:
            os.write(buffer1, 0, len1);
        }
//        os.write("你好，这里是0101".getBytes());
        
        //5.关闭数据的输出
        socket.shutdownOutput();
        
        //6.接受来自服务端的回应，显示到控制台上
        is = socket.getInputStream();
        baos = new ByteArrayOutputStream();
        byte[] buffer2 = new byte[9];
        int len2;
        while ((len2 = is.read(buffer2)) != -1) {
            baos.write(buffer, 0, len);
        }
        System.out.println(baos.toString());
    } catch(IOException e) {
        e.printStackTrace();
    } finally {
        //4.资源关闭
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```



```java
//服务端
@Test
public void service() {
    ServerSocket serverSocket = null;
    Socket socket = null;
    InputStream is = null;
    OutputStream os = null;
    FileOutputStream fos = null;
    try {
        //1.创建服务器端的ServerSocket，指明自己的端口号
        serverSocket = new ServerSocket(8989);
        
        //2.调用accept()，接受来自客户端的socket
        socket = serverSocket.accept();
        
        //3.获取输入流
        is = socket.getInputStream();
        
        //4.获取文件流
        fos = new FileOutputStream(new File("dest.jpg"));
        
        //5.读取数据
        byte[] buffer = new byte[7];
        int len;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        System.out.println("传输完成...");
        
        //6.服务器端给与客户端反馈
        os = socket.getOutputStream();
        os.write("已经收到文件！！！".getBytes());
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //7.资源关闭
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```



#### 4）UDP网络编程

```java
//发送端
@Test
public void sender() {
    DatagramSocket socket = null;
    try {
        socket = new DatagramSocket();

        String str = "UDP方式...";
        byte[] data = str.getBytes();
        InetAddress inet = InetAddress.getByName("127.0.0.1");
//      InetAddress inet = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(data, 0, data.length, inet, 2988);

        socket.send(packet);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        socket.close();
    }
}
```

```java
//接收端
@Test
public void receiver() {
    DatagramSocket socket = null;
    try {
        socket = new DatagramSocket(2988);

        byte[] buffer = new byte[100];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);

        socket.receive(packet);

        System.out.println(new String(packet.getData(), 0, packet.getLength()));
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        socket.close();
    }
}
```



#### 4）URL编程

```java
//实现Tomcat服务端数据下载
public static void main(String[] args) {
    HttpURLConnection urlConnection = null;
    InputStream is = null;
    FileOutputStream fos = null;
    try {
        URL url = new URL("http://baidu.com");

        //得到连接对象
        urlConnection = (HttpURLConnection) url.openConnection();

        //获取连接
        urlConnection.connect();

        //获取输入流
        is = urlConnection.getInputStream();
        fos = new FileOutputStream("dest.txt");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        //public String getProtocol() 获取协议名

        //public String getHost() 获取主机名

        //public String getPort() 获取端口号

        //public String getPath() 获取文件路径

        //public String getFile() 获取文件名
        
        //public String getQuery() 获取URL的查询名
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //关闭资源
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }
}
```



## Java基础14

### Java Reflection（反射）

> + ``Reflection``：动态语言的关键，允许程序在执行期借助``Reflection API``取得任何类的内部信息，直接操作任意类的内部属性和方法
> + 加载完类后，堆内存的方法区，产生了一个``Class``类型的对象，其包含了完整的类的结构信息，通过这个对象看到类的内部结构——反射

> + 正常方式：引入包类的名称—>``new`` 实例化—>取得实例化对象
> + 反射方式：实例化对象—>``getClass()``方法—>得到完整的”包类“名称

#### 1）反射提供的功能

+ 在运行时判断任意一个对象所属的类
+ 在运行时构造任意一个类的对象
+ 在运行时判断任意一个类所具有的成员变量和方法
+ 在运行时获取泛型方法
+ 在运行时调用任意一个对象的成员变量和方法
+ 在运行时处理注解
+ 生成**动态**代理



```java
/*通过Java反射机制得到类的包名和类名*/
public static void Demo01() {
    Person person = new Person();
    System.out.println("Demo01：包名：" + person.getClass().getPackage().getName() +
                       ", 完整类名：" + person.getClass().getName());
}

/*证所有的类都是Class类的实例对象*/
public static void Demo02() throws ClassNotFoundException {
    /*定义两个未知类型的Class，并赋值为null*/
    Class<?> class1 = null;
    Class<?> class2 = null;

    /*写法一，可能抛异常*/
    class1 = Class.forName("Reflection.ReflectionMechanism.Person");
    System.out.println("Demo02(写法一)包名：" + class1.getPackage().getName() +
                       ", 完整类名：" + class1.getName());

    /*写法二*/
    class2 = Person.class;
    System.out.println("Demo02(写法二)包名：" + class1.getPackage().getName() +
                       ", 完整类名：" + class1.getName());
}

/*   * 通过java反射机制，用Class创建类对象
     * java反射的意义所在*/
public static void Demo03() throws ClassNotFoundException, IllegalAccessException,
InstantiationException {
    Class<?> class1 = null;
    class1 = Class.forName("Reflection.ReflectionMechanism.Person");
    /*实例化类Person，不能带参数，必须选择无参构造函数*/
    Person person = (Person)class1.newInstance();
    person.setAge(23);
    person.setName("Torking");
    System.out.println("Demo03：" + person.getName() + " : " + person.getAge());
}

/*通过java反射机制得到一个类的构造函数，并实现创建带参实例对象*/
public static void Demo04() throws ClassNotFoundException, IllegalAccessException,
InvocationTargetException, InstantiationException {
    Class<?> class1 = null;
    Person person1 = null;
    Person person2 = null;

    class1 = Class.forName("Reflection.ReflectionMechanism.Person");
    /*得到构造函数的集合*/
    Constructor<?>[] constructors = class1.getConstructors();
    person1 = (Person)constructors[0].newInstance();
    person1.setAge(23);
    person1.setName("PlusTorking");

    person2 = (Person)constructors[1].newInstance(23, "Accepted");
    System.out.println("Demo04：" + person1.getAge() + " : " + person1.getName() + ", " +
                       person2.getAge() + " : " + person2.getName());
}

/*通过java反射机制操作成员变量，set get*/
public static void Demo05() throws ClassNotFoundException, IllegalAccessException,
InstantiationException, NoSuchFieldException {
    Class<?> class1 = null;
    class1 = Class.forName("Reflection.ReflectionMechanism.Person");
    Object obj = class1.newInstance();

    Field personNameField = class1.getDeclaredField("name");
    personNameField.setAccessible(true);
    personNameField.set(obj, "胖虎先森");

    System.out.println("Demo05修改后的属性的值：" + personNameField.get(obj));
}

/*通过Java反射机制得到类的一些属性： 继承的接口，父类，函数信息，成员信息，类型等*/
public static void Demo06() throws ClassNotFoundException {
    Class<?> class1 = null;
    class1 = Class.forName("Reflection.ReflectionMechanism.SuperMan");

    //获取父类名称
    Class<?> superClass = class1.getSuperclass();
    System.out.println("Demo06: superMan类的父类名：" + superClass.getName());

    Field[] fields = class1.getDeclaredFields();
    for (Field field : fields) {
        System.out.println("类的成员：" + field);
    }
    System.out.println("+++++++++++++++++");

    //取得类方法
    Method[] methods = class1.getDeclaredMethods();
    for (int i=0; i<methods.length; i++) {
        System.out.println("Demo06，取得superMan类的方法：");
        System.out.println("函数名：" + methods[i].getName());
        System.out.println("函数返回类型：" + methods[i].getReturnType());
        System.out.println("函数访问修饰符：" + Modifier.toString(methods[i].getModifiers()));
        System.out.println("函数代码写法：" + methods[i]);
    }
    System.out.println("+++++++++++++++++");

    //取得类实现的接口，因为接口类也属于Class,所以得到接口中的方法也是一样的方法得到
    Class<?>[] interfaces = class1.getInterfaces();
    for (int i=0; i<interfaces.length; i++) {
        System.out.println("实现的接口类名：" + interfaces[i].getName());
    }
}

/*通过Java反射机制调用类方法*/
public static void Demo07() throws ClassNotFoundException, NoSuchMethodException,
IllegalAccessException, InstantiationException, InvocationTargetException {
    Class<?> class1 = null;
    class1 = Class.forName("Reflection.ReflectionMechanism.SuperMan");

    System.out.println("Demo07：调用无参方法fly(): ");
    Method method = class1.getMethod("fly");
    method.invoke(class1.newInstance());

    System.out.println("Demo07：调用有参方法walk(int m): ");
    method = class1.getMethod("walk", int.class);       //walk, 参数
    method.invoke(class1.newInstance(), 100);
}

/*通过java反射机制得到类加载信息*/
public static void Demo08() throws ClassNotFoundException {
    Class<?> class1 = null;
    class1 = Class.forName("Reflection.ReflectionMechanism.SuperMan");
    String name = class1.getClassLoader().getClass().getName();

    System.out.println("Demo08：类加载器名：" + name);
}
```



#### 2）``java.lang.Class``类

+ 类的加载过程：

  > 程序经过``java.exe``命令后，生成一个或多个字节码文件(``.class``)，用``java.exe``命令对字节码文件解释运行——类的加载

+ 运行时类：

  > - 加载到内存中的类，称为运行时类，作为Class的一个实例
  >
  > - Class的实例对应着一个运行时类
  >
  > - 加载到内存的运行时类，会缓存一段时间，在此时间内可以通过不同的获取方式获取此运行时类（Calss）



#### 3）*获取* Class实例的方式

+ 方式一：调用运行时类的属性

```java
Class class1 = Person.class;
System.out.println(class1);
Object obj = class1.getInstance();		//获取运行时类的对象
```



+ 方式二：通过运行时类的对象

```java
Person person = new Person();
Class class2 = person.getClass();
```



+ 方式三：调用Class的静态方法：``forName(String classPath)``，更好的体现了动态性

```java
//会抛异常
Class class3 = Class.forName("Reflection.ReflectionMechanism.Person");
```



+ 方式四：使用类的加载器

```java
ClassLoader classLoader = ReflectionTest.class.getClassLoader();
Class class4 = classLoader.loadClass("Reflection.ReflectionMechanism.Person");
```

```java
System.out.println(class1 == class2);		//true
System.out.println(class1 == class3);		//true
System.out.println(class1 == class4);		//true
//虽然获取的方式不一样，但获取的是同一个运行时类，即Class对应一个运行时类
```



+ 能够获取Class实例
  1. class
  2. interface：接口
  3. []：数组：只要类型和维度一样，就是同一个class
  4. enum：枚举
  5. annotation：注解@interface
  6. primitive：基本数据类型
  7. void



#### 4）类的加载器

```java
//使用系统类的加载器
ClassLoader classLoader = DemoTest03.class.getClassLoader();
System.out.println(classLoader);

//调用系统类的加载器的getParent()：获取扩展类加载器
ClassLoader classLoader1 = classLoader.getParent();
System.out.println(classLoader1);

//无法获取引导类加载器，
//引导类加载器负载加载java的核心类库，无法加载自定义类的
ClassLoader classLoader2 = classLoader1.getParent();
System.out.println(classLoader2);		//null
ClassLoader classLoader3 = String.class.getClassLoader();
System.out.println(classLoader3);		//null
```



#### 5）创建运行时类的对象

```java
/*
* newInstance()：调用此方法，创建对应的运行时类对象，内部调用运行时类的     * ********空参构造器*********
*/
Class<Person> clazz = Person.class;
Person obj = clazz.newInstance();
```

```java
//获取运行时类父类的泛型
@Test
public void test03() {
    Class clazz = SuperMan.class;
    Type genericSuperclass = clazz.getGenericSuperclass();
    ParameterizedType paramType = (ParameterizedType)genericSuperclass;
    //获取泛型类型
    Type[] actualTypeArguments = paramType.getActualTypeArguments();
    System.out.println(actualTypeArguments[0].getTypeName());
    System.out.println(((Class) actualTypeArguments[0]).getName());
}

//获取运行时类的接口
@Test
public void test04() {
    Class clazz = Person.class;
    try {
        Class class1 = Class.forName("Reflection.ReflectionMechanism.Person");
        Class[] interfaces = class1.getInterfaces();
        for (Class c : interfaces) {
            System.out.println(c);
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    Class[] interfaces = clazz.getInterfaces();
    for (Class c : interfaces) {
        System.out.println(c);
    }

    Class[] interfaces1 = clazz.getSuperclass().getInterfaces();
    for (Class c : interfaces1) {
        System.out.println(c);
    }
}
```



#### 6）调用运行时类的指定结构

+ 获取指定的属性

```java
public void test01() throws Exception {
    Class clazz = Person.class;

    //创建运行时类的对象
    Person person = (Person) clazz.newInstance();

    //获取指定的属性****(需要声明为public)****
    Field field = clazz.getField("id");

    //设置当前属性的值
    field.set(person, 1000);

    //获取当前对象person的当前属性值
    Object o = field.get(person);
    System.out.println(o);

    //throw Exception
    /*
    Field field1 = clazz.getField("salary");
    field1.set(person, 20000.0);
    Object o1 = field1.get(person);
    System.out.println(o1);
    */
    
    //获取指定的属性*****(至少为默认权限)******可以拿到name对象
    Field name = clazz.getDeclaredField("name");
    //没有设置setAccessible(true)则获取指定的属性*****(至少为默认权限)******
 	name.setAccessible(true);		//保证当前属性可访问即便private
    name.set(person, "Plus");
    Object o2 = name.get(person);
}
```



+ 获取指定的方法

```java
Class clazz = Person.class;
Person p = (Person) clazz.newInstance();

//参数1：方法名、参数2：形参列表
Method method = clazz.getDeclaredMethod("show", String.class);
//私有的方法需要setAccessible(true)
show.setAccessible(true);
//参数1：方法调用者、参数2：方法的实参（给形参赋值）
//invoke的返回值为对应方法的返回值
show.invoke(p, "CHN");
```



#### 7）动态代理

