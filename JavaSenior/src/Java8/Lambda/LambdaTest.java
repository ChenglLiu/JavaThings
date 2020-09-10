package Java8.Lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda表达式
 * @author liuclo
 * @Date 2020/9/9 22:30
 */

public class LambdaTest {
    @Test
    //语法格式一：无参、无返回值
    public void test01() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Aloha, World!!");
            }
        };
        runnable.run();

        System.out.println("***********Lambda***********");
        Runnable r = () -> System.out.println("Hello, World..");
        r.run();
    }

    @Test
    //语法格式二：Lambda需要一个参数，但是没有返回值
    public void test02() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("Ni Hao");

        System.out.println("************Lambda表达式************");
        Consumer<String> con = (String s) -> {
            System.out.println(s);
        };
        con.accept("Aha...");

        //语法格式三：数据类型可以省略，可以由编译器推断得出，称为“类型推断”
        Consumer<String> c = (s) -> {
            System.out.println(s);
        };
        c.accept("Hi......");


    }

    @Test
    //语法格式三：数据类型可以省略，可以由编译器推断得出，称为“类型推断”
    public void test03() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        System.out.println(comparator.compare(12, 22));

        System.out.println("-------------Lambda-----------");
        Comparator<Integer> com = (o1, o2) -> Integer.compare(o1, o2);
        System.out.println(com.compare(12, 22));

        System.out.println("**************方法引用***********");
        Comparator<Integer> comp = Integer::compare;
        System.out.println(comp.compare(17, 14));
    }

    @Test
    //语法格式四：Lambda若只需要一个参数时，参数的小括号可以省略
    public void test04() {
        Consumer<String> consumer = (s) -> {
            System.out.println(s);
        };
        consumer.accept("plus");

        System.out.println("-------------------------");
        Consumer<String> con = s -> {
            System.out.println(s);
        };
        con.accept("torking");
    }

    @Test
    //语法格式五：Lambda需要两个及以上的参数，多条执行语句，并且可以有返回值
    public void test05() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1 - o2);
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator.compare(21, 13));

        System.out.println("*************Lambda表达式***************");
        Comparator<Integer> com = (o1, o2) -> {
            System.out.println(o1 - o2);
            return o1.compareTo(o2);
        };
        System.out.println(com.compare(21, 27));
    }

    @Test
    //语法格式六：当Lambda只有一条语句，return和{}若有，都可以省略
    public void test06() {
        Comparator<Integer> comparator = (o1, o2) -> {
            return Integer.compare(o1, o2);
        };
        System.out.println(comparator.compare(17, 22));

        System.out.println("--------------------");
        Comparator<Integer> com = (o1, o2) -> Integer.compare(o1, o2);
        System.out.println(com.compare(21, 18));
    }

}
