package Java8.Lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author liuclo
 * @Desciption //TODO
 * @Date 2020/9/12 17:18
 **/

public class MethodReference {
    //情况一：对象::实例方法
    @Test
    //Consumer<> 中的 void accept(T t)
    //PrintStream 中的 void println(T t)
    public void test01() {
        Consumer<String> consumer = str -> System.out.println("forever : " + str);
        consumer.accept("教员");

        System.out.println("-----------Method Reference----------");
        PrintStream ps = System.out;
        Consumer<String> con = ps::println;
        con.accept("图书管理员");
    }

    @Test
    //Supplier<> 的 T get()
    //Person 中的 String getName()
    public void test02() {
        Person person = new Person(23, "Plus");
        Supplier<String> supplier = () -> person.getName();
        System.out.println(supplier.get());

        System.out.println("------------Normal Implements------------");
        Supplier<Integer> sup = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return person.getAge();
            }
        };
        System.out.println(sup.get());

        System.out.println("-------------Method Reference-------------");
        Supplier<String> supp = person::getName;
        System.out.println(supp.get());
        Supplier<Integer> suppl = person::getAge;
        System.out.println(suppl.get());
    }

    //情况二：类 :: 静态方法
    @Test
    //Comparator 中的 int compare(T t1, T t2)
    //Integer 中的 int compare(T t1, T t2)
    public void test03() {
        Comparator<Integer> comparator = (t1, t2) -> -Integer.compare(t1, t2);
        System.out.println(comparator.compare(12, 67));

        System.out.println("-------------Method Reference------------");
        Comparator<Integer> com = Integer::compare;
        System.out.println(com.compare(23, 67));

        System.out.println("-------------another case---------------");
        Comparator<String> co = (s1, s2) -> -s1.compareTo(s2);
        System.out.println(co.compare("abb", "aab"));

        Comparator<String> comp = String::compareTo;
        System.out.println(comp.compare("abb", "aab"));
    }

    @Test
    //Function 中的 R apply(T t)
    //Math 中的 Long round(Double d)
    public void test04() {
        System.out.println("----------Normal implements----------");
        Function<Double, Long> function = new Function<Double, Long>() {
            @Override
            public Long apply(Double aDouble) {
                return Math.round(aDouble);
            }
        };
        System.out.println(function.apply(12.3456));

        System.out.println("-----------Lambda expression------------");
        Function<Double, Long> func = d -> Math.round(d);
        System.out.println(function.apply(23.456));

        System.out.println("-------------Method Reference-------------");
        Function<Double, Long> fu = Math::round;
        System.out.println(fu.apply(34.567));
    }

    //情况三：类 :: 实例方法（***）
    @Test
    //Comparator 中的 int compare(T t1, T t2)
    //String 中的 int s1.compareTo(s2)
    public void test05() {
        test03();
    }

    @Test
    //BiPredicate 中的 boolean test(T t1, T t2)
    //String 中的 boolean s1.equals(s2)
    public void test06() {
        BiPredicate<String, String> biPredicate = (s1, s2) -> s1.equals(s2);
        System.out.println(biPredicate.test("plus", "plus"));

        System.out.println("-----------Method Reference-------------");
        BiPredicate<String, String> predicate = String::equals;
        System.out.println(predicate.test("aloha", "hello"));
    }

    @Test
    //Function 中的 R apply(T t)
    //People 中的 String getName()
    public void test07() {
        Function<Person, String> function = person -> person.getName();
        String s = function.apply(new Person(23, "plus"));
        System.out.println(s);

        System.out.println("--------------Method Reference-------------");
        Person person = new Person(23, "xian");
        Function<Person, Integer> func = Person::getAge;
        System.out.println(func.apply(new Person(23, "plus")));
        System.out.println(func.apply(person));
    }
}

class Person {
    private int age;
    private String name;

    public Person() {
    }

    public Person(int age) {
        this.age = age;
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}