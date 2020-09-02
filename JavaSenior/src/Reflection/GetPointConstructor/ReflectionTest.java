package Reflection.GetPointConstructor;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 获取运行时类的指定结构
 */

public class ReflectionTest {
    @Test
    public void test01() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class clazz = Person.class;

        //创建运行时类的对象
        Person person = (Person) clazz.newInstance();

        //获取指定的属性(需要声明为public)
        Field field = clazz.getField("id");

        //设置当前属性的值
        field.set(person, 1000);

        //获取当前对象person的当前属性值
        Object o = field.get(person);
        System.out.println(o);

        //throw Exception
        /*
        Field field1 = clazz.getField("salary");
        field1.setAccessible(true);
        field1.set(person, 20000.0);
        Object o1 = field1.get(person);
        System.out.println(o1);
         */


        Field name = clazz.getDeclaredField("name");
        //没有设置setAccessible(true)则获取指定的属性*****(至少为默认权限)******
        name.setAccessible(true);
        Field id = clazz.getDeclaredField("id");
        name.set(person, "Plus");
        Object o2 = name.get(person);
        id.set(person, 23);
        Object o3 = id.get(person);
        System.out.println(o2);
        System.out.println(o3);
    }
}

class Person {
    public int id;
    double salary;
    private String name;
}
