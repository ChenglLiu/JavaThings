package Generic.WildCard;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 泛型在继承方面的体现 和 通配符的使用
 * @author liucl
 * @date 2020/8/15 15:22
 */

public class GenericWildCast {
    @Test
    public void test01() {
        Object obj = null;
        String str = null;
        obj = str;

        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2;

        List<String> list1 = null;
        List<Object> list2 = null;
        //list1 list2 不具备子父类关系，而是并列关系
        //list2 = list1;

        List<?> list = null;

        List<String> list3 = new ArrayList<>();
        list3.add("AA");
        list3.add("BB");
        list3.add("CC");

        list = list3;

        //不能向List<?>内部添加数据，除了null
        list.add(null);

        //允许获取数据
        Object o = list.get(2);
        System.out.println(o);

        print(list3);
    }

    public void print(List<?> list) {
        Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            System.out.println(obj);
        }
    }

    /**
     * @Descriotion 优先值条件的通配符使用
     *      ? extends Person
     *      ? super Person
     * @author liucl
     * @date 2020/8/15 22:45
     */
    @Test
    public void test02() {
        List<? extends Person> list1 = null;
        List<? super Person> list2 = null;

        List<Student> list3 = new ArrayList<Student>();
        List<Person> list4 = new ArrayList<Person>();
        List<Object> list5 = new ArrayList<Object>();

        list1 = list3;
        list1 = list4;
        //编译错误
        //list1 = list5;

        Person person = list1.get(0);
        //编译错误
        //Student student = list1.get(0);

        //编译错误
        //list2 = list3;
        list2 = list4;
        list2 = list5;

        //编译错误
        //Person person1 = list2.get(0);
        Object object = list2.get(0);

        //写
    }
}

class Person {

}

class Student extends Person {

}
