package Collentions.Collection;

import org.junit.Test;

import java.util.*;

public class SetTest {
    @Test
    public void test1() {
        //无序性不等于随机性
        Set set = new HashSet();
        set.add(54);
        set.add(77);
        set.add(new Person("PlusTorking", 23));
        set.add("cc");
        set.add(77);
        set.add(new Person("PlusTorking", 23));

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test2() {
        //LinkedHashSet：遍历时按照添加的顺序，维护了两个引用（双向链表）
        //在频繁的遍历操作中，其效率高于HashSet
        Set set = new LinkedHashSet();
        set.add(54);
        set.add(77);
        set.add(new Person("PlusTorking", 23));
        set.add(54);
        set.add("cc");
        set.add(77);
        set.add(new Person("PlusTorking", 23));

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    //自然排序
    public void test3() {
        //TreeSet
        TreeSet set = new TreeSet();

        set.add(123);
        set.add(456);
        set.add(-234);
        set.add(999);
//        set.add("AA");
//        set.add(new Person("PlusTorking", 23));

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("---------------");

        TreeSet treeSet = new TreeSet();
        treeSet.add(new Person("Tom", 27));
        treeSet.add(new Person("Jack",32));
        treeSet.add(new Person("Pony",30));
        treeSet.add(new Person("Jim", 7));
        treeSet.add(new Person("Tom",16));

        for (Object obj : treeSet) {
            System.out.println(obj);
        }
    }

    @Test
    //定制排序
    public void test4() {
        Comparator com = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Person && o2 instanceof Person) {
                    Person p1 = (Person)o1;
                    Person p2 = (Person)o2;
                    if (p1.getName().equals(p2.getName())) {
                        return -Integer.compare(p1.getAge(), p2.getAge());
                    } else {
                        return -p1.getName().compareTo(p2.getName());
                    }
                } else {
                    throw new RuntimeException("输入类型不匹配...");
                }
            }
        };
        TreeSet treeSet = new TreeSet(com);
        treeSet.add(new Person("Tom", 27));
        treeSet.add(new Person("Jack",32));
        treeSet.add(new Person("Pony",30));
        treeSet.add(new Person("Jim", 7));
        treeSet.add(new Person("Tom",16));

        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        treeSet.clear();
        System.out.println(treeSet.size());
    }
}
