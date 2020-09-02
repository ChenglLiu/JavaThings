package Reflection.ReflectionMechanism;

public class DemoTest02 {
    public static void main(String[] args) {
        //方式一：调用运行时类的属性
        Class class1 = Person.class;
        System.out.println(class1);

        //方式二：通过运行时类的对象
        Person person = new Person();
        Class class2 = person.getClass();

        //方式三：调用Class的静态方法
        Class class3 = null;
        try {
            class3 = Class.forName("Reflection.ReflectionMechanism.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(class1 == class2);
        System.out.println(class1 == class3);

        //方式四：使用类的加载器
        Class class4 = null;
        ClassLoader classLoader = DemoTest02.class.getClassLoader();
        try {
            class4 = classLoader.loadClass("Reflection.ReflectionMechanism.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(class4);

        System.out.println(class1 == class4);
    }
}
