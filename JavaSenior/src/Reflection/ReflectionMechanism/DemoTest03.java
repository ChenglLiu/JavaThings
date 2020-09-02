package Reflection.ReflectionMechanism;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Random;

public class DemoTest03 {
    @Test
    public void test01() {
        //类的加载器
        //使用系统类的加载器
        ClassLoader classLoader = DemoTest03.class.getClassLoader();
        System.out.println(classLoader);

        //调用系统类的加载器的getParent()：获取扩展类加载器
        ClassLoader classLoader1 = classLoader.getParent();
        System.out.println(classLoader1);

        //无法获取引导类加载器，引导类加载器负载加载java的核心类库，无法加载自定义类的
        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2);
    }

    @Test
    public void test02() {
        for (int i=0; i<100; i++) {
            int num = new Random().nextInt(3);
            String classPath = "";
            switch (num) {
                case 0:
                case 1:
                    classPath = "Reflection.ReflectionMechanism.SuperMan";
                    break;
                case 2:
                    classPath = "Reflection.ReflectionMechanism.Person";
                    break;
            }
            try {
                Object obj = getInstance(classPath);
                System.out.println(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Object getInstance(String classPath) throws Exception {
        Class clazz = Class.forName(classPath);
        return clazz.newInstance();
    }

    /**
     * 获取运行时类父类的泛型
     */
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

    /**
     * 获取运行时类的接口
     */
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

    /**
     * 获取运行时类所在的包
     */
    @Test
    public void test05() {
        Person person = new Person();
        Class clazz = person.getClass();

        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);
    }

    /**
     * 获取运行时类声明的注解
     */
    @Test
    public void test06() {
        try {
            Class clazz = Class.forName("Reflection.ReflectionMechanism.SuperMan");
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation a : annotations) {
                System.out.println(a);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
