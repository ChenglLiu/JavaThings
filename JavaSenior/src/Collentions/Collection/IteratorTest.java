package Collentions.Collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
}
