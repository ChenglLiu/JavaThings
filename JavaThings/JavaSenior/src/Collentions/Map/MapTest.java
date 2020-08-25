package Collentions.Map;

import org.junit.Test;

import java.util.*;

public class MapTest {
    @Test
    public void test1() {
        Map map = new HashMap();
        map.put("AA", 123);
        map.put(45, 123);
        map.put("AA", 87);
        map.put("BB", 99);
        map.put(123, 999);

        Map map1 = new HashMap();
        map1.put("CC", 77);
        map1.putAll(map);

        System.out.println(map);
        System.out.println(map1);

        Object value = map1.remove(45);
        System.out.println(value);
        System.out.println(map1);

        System.out.println(map1.get(45));

        System.out.println(map.containsKey("AA"));

        System.out.println(map.containsValue(123));
    }

    /**
     * @Description 元视图
     *      Set keySet()：返回所有key构成的Set集合
     *      Collection values()：返回所有value构成的Collection集合
     *      Set entrySet()：返回所有的key-value对构成的Set集合
     * @author liucl
     * @date 2020/8/13 21:21
     */
    @Test
    public void test2() {
        Map map = new HashMap();
        map.put("AA", 123);
        map.put("BB", 233);
        map.put(77, 123);
        map.put("CC", 999);
        map.put("DD", 66);

        //遍历所有的key集合
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("-----------");

        //遍历所有的value集合
        Collection values = map.values();
        for (Object obj : values) {
            System.out.println(obj);
        }

        System.out.println("-------------");

        //遍历所有的key-value对
        //方式一：
        Set entrySet = map.entrySet();
        iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            //entrySet集合中的元素都是entry，强转
            Map.Entry entry = (Map.Entry)object;
            System.out.println(entry.getKey() + "--->" + entry.getValue() );
        }
        System.out.println("*********");
        //方式二：
        Set keySet = map.keySet();
        iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            Object value = map.get(key);
            System.out.println(key + "--->" + value);
        }
    }

    //自然排序
    @Test
    public void test3() {
        TreeMap treeMap = new TreeMap();

        Person p1 = new Person("Tom", 23);
        Person p2 = new Person("Jerry", 35);
        Person p3 = new Person("Jack", 21);
        Person p4 = new Person("Rose", 19);
        Person p5 = new Person("Jack", 42);

        treeMap.put(p1, 98);
        treeMap.put(p2, 89);
        treeMap.put(p3, 77);
        treeMap.put(p4, 57);
        treeMap.put(p5, 100);

        Set entrySet = treeMap.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            Map.Entry entry = (Map.Entry)obj;
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }

    //定制排序
    @Test
    public void test4() {
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

        Person p1 = new Person("Tom", 23);
        Person p2 = new Person("Jerry", 35);
        Person p3 = new Person("Jack", 21);
        Person p4 = new Person("Rose", 19);
        Person p5 = new Person("Jack", 42);

        treeMap.put(p1, 98);
        treeMap.put(p2, 89);
        treeMap.put(p3, 77);
        treeMap.put(p4, 57);
        treeMap.put(p5, 100);

        Set entrySet = treeMap.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            Map.Entry entry = (Map.Entry)obj;
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
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
                return -Integer.compare(this.age, person.age);
            } else {
                return this.name.compareTo(person.name);
            }

//            return this.name.compareTo(person.name);
        } else {
            throw new RuntimeException("输入类型不匹配...");
        }
    }
}
