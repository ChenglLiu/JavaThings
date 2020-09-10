package Collentions.Collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {
    @Test
    public void test1() {
        Collection c = new ArrayList();
        c.add("Plus");
        c.add(new String("Torking"));
        c.add(new Person("I Swear", 23));
        c.add(1997);
        c.add(false);

        Iterator iterator = c.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

//        while (iterator.next() != null) {
//            System.out.println(iterator.next());
//        }
    }

    @Test
    public void test02() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("def");

        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
