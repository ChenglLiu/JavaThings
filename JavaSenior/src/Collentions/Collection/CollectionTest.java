package Collentions.Collection;

import org.junit.Test;

import java.util.*;

public class CollectionTest {
    @Test
    public void test1() {
        Collection c = new ArrayList();

        //add(Object o)
        c.add("Torking");
        c.add("Plus");

        //size()
        System.out.println(c.size());

        //addAll()
        Collection c1 = new ArrayList();
        c1.add("Hao");
        c1.addAll(c);
        System.out.println(c1.size());
        System.out.println(c1);

        //isEmpty()
        System.out.println(c1.isEmpty());

        //clear()
        c1.clear();
        System.out.println(c1.isEmpty());
    }

    @Test
    public void test2() {
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new String("Torking"));
        coll.add(new Person("Plus", 23));

        //contains() 判断是否在集合中出现过，会调用obj对象所在类的equals()
        System.out.println(coll.contains(new String("Torking")));
        System.out.println(coll.contains(new Person("Plus", 23)));
    }

    @Test
    public void test3() {
        List<int[]> list = Arrays.asList(new int[]{123, 23});
        List arr = Arrays.asList(new Integer[]{123, 4656});
        System.out.println(list);
        System.out.println(arr);
    }
}

class Person implements Comparable {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "name:" + name + ", age:" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("Person equals()...");
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person p = (Person)obj;
        return age == p.age && Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Person) {
            Person person = (Person)o;

            if (this.name.equals(person.name)) {
                return Integer.compare(this.age, person.age);
            } else {
                return this.name.compareTo(person.name);
            }

//            return this.name.compareTo(person.name);
        } else {
            throw new RuntimeException("输入类型不匹配...");
        }
    }
}
