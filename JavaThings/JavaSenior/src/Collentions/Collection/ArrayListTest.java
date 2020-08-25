package Collentions.Collection;

import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayListTest {
    @Test
    public void test1() {
        ArrayList list = new ArrayList();
        //add()
        list.add(123);
        list.add("aa");
        list.add(new Person("Plus", 23));
        list.add(123);

        System.out.println(list);

        //void add(int index, Object obj)
        list.add(1, "bb");
        System.out.println(list);

        //boolean addAll(Collection c)
        List list1 = Arrays.asList(1, 2, 3);
        list.addAll(list1);
//        list.add(list1);
        System.out.println(list);
        System.out.println(list.size());

        //iterator
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }




    }
}
