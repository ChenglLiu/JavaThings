package Java8.Lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author liuclo
 * @Desciption //TODO
 * @Date 2020/9/14 22:20
 **/

public class ConstructorRefTest {
    @Test
    public void test01() {
        Supplier<Person> supplier = new Supplier<Person>() {
            @Override
            public Person get() {
                return new Person();
            }
        };
        Person person = supplier.get();
        System.out.println(person);     //Person{age=0, name='null'}

        System.out.println("-------------Lambda expression-----------");
        Supplier<Person> supp = () -> new Person();
        Person person1 = supp.get();
        person1.setAge(23);

        System.out.println("------------Constructor Reference-----------");
        Supplier<Person> su = Person::new;
        Person person2 = su.get();
        person2.setName("plus");
    }

    @Test
    public void test02() {
        Function<Integer, Person> function = age -> new Person(age, "AlohaWorld...");
        Person person = function.apply(23);
        System.out.println(person);     //Person{age=23, name='AlohaWorld...'}

        System.out.println("------------Constructor Reference-----------");
        Function<Integer, Person> func = Person::new;
        Person p = func.apply(23);
        System.out.println(p);          //Person{age=23, name='null'}
    }

    @Test
    public void test03() {
        BiFunction<Integer, String, Person> biFunction = (age, name) -> new Person(age, name);
        Person person = biFunction.apply(23, "plus");
        System.out.println(person);         //Person{age=23, name='plus'}

        System.out.println("------------Constructor Reference------------");
        BiFunction<Integer, String, Person> function = Person::new;
        Person p = function.apply(23, "Aloha");
        System.out.println(p);              //Person{age=22, name='Aloha'}
    }

    @Test
    public void test04() {
        Function<Integer, String[]> function = length -> new String[length];
        String[] arr = function.apply(7);
        System.out.println(Arrays.toString(arr));

        System.out.println("---------------Constructor Reference----------------");
        Function<Integer, String[]> func = String[]::new;
        String[] strings = func.apply(8);
        System.out.println(Arrays.toString(strings));
    }
}
