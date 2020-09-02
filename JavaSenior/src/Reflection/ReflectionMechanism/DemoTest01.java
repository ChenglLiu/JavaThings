package Reflection.ReflectionMechanism;

import org.junit.Test;

import java.lang.reflect.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @Description java反射机制，把类、类的属性当成一个对象来操作
 * @author liucl
 * @date 2020/8/26 14:50
 */

public class DemoTest01 {
    /*把异常抛给虚拟机处理*/
    public static void main(String[] args) throws Exception {
        //Demo01
        Demo01();
        System.out.println("_________________");

        //Demo02()
        Demo02();
        System.out.println("_________________");

        //Demo03()
        Demo03();
        System.out.println("_________________");

        //Demo04()
        Demo04();
        System.out.println("_________________");

        //Demo05()
        Demo05();
        System.out.println("_________________");

        //Demo06()
        Demo06();
        System.out.println("_________________");

        //Demo07()
        Demo07();
        System.out.println("_________________");

        //Demo08()
        Demo08();
        System.out.println("_________________");

        Class<?> clazz = Person.class;
        Constructor constructor = clazz.getDeclaredConstructor(int.class, String.class);
        constructor.setAccessible(true);
        Person person = (Person) constructor.newInstance(23, "xiaoming");
        System.out.println(person);
    }

    @Test
    public void test() {
        Date date = new Date(2020, 6, 1);
        System.out.println(date.toString());
        System.out.println(date.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat();
        System.out.println(sdf.format(date));

        String str = "2020-10-1";
        try {
            System.out.println(sdf.parse(str));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 通过Java反射机制得到类的包名和类名
     */
    public static void Demo01() {
        Person person = new Person();
        System.out.println("Demo01：包名：" + person.getClass().getPackage().getName() +
                ", 完整类名：" + person.getClass().getName());
    }

    /**
     * 验证所有的类都是Class类的实例对象
     */
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

    /**
     * 通过java反射机制，用Class创建类对象
     * java反射的意义所在
     */
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

    /**
     * 通过java反射机制得到一个类的构造函数，并实现创建带参实例对象
     */
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

    /**
     * 通过java反射机制操作成员变量，set get
     */
    public static void Demo05() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        Class<?> class1 = null;
        class1 = Class.forName("Reflection.ReflectionMechanism.Person");
        Object obj = class1.newInstance();

        Person o = (Person)obj;
        o.setAge(23);

        Field personNameField = class1.getDeclaredField("name");
        personNameField.setAccessible(true);
        personNameField.set(obj, "胖虎先森");

        o.setName("静香");

        System.out.println("Demo05修改后的属性的值：" + personNameField.get(obj));
    }

    /**
     * 通过Java反射机制得到类的一些属性： 继承的接口，父类，函数信息，成员信息，类型等
     */
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
            System.out.println("函数访问修饰符：" + methods[i].getModifiers());
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

    /**
     * 通过Java反射机制调用类方法
     */
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

    /**
     * 通过java反射机制得到类加载信息
     */
    public static void Demo08() throws ClassNotFoundException {
        Class<?> class1 = null;
        class1 = Class.forName("Reflection.ReflectionMechanism.SuperMan");
        String name = class1.getClassLoader().getClass().getName();

        System.out.println("Demo08：类加载器名：" + name);
    }
}

interface ActionInterface {
    public void walk(int m);
}

class Person {
    private int age;
    private String name;

    public Person() {
    }

//    private Person(String name) {
//        this.name = name;
//    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class SuperMan extends Person implements ActionInterface {
    private boolean blueBriefs;

    public void fly() {
        System.out.println("超人会飞...");
    }

    public boolean isBlueBriefs() {
        return blueBriefs;
    }

    public void setBlueBriefs(boolean blueBriefs) {
        this.blueBriefs = blueBriefs;
    }

    @Override
    public void walk(int m) {
        System.out.println("超人会飞~~~~飞了" + m + "公里，飞累了");
    }
}