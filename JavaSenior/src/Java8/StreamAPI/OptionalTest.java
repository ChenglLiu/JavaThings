package Java8.StreamAPI;

import org.junit.Test;

import java.util.Optional;

/**
 * @author liuclo
 * @Desciption //TODO
 * @Date  2020/9/16 22:47
 **/

public class OptionalTest {
    @Test
    public void test01() {
        Person person = new Person();
        Optional<Person> optionalPerson = Optional.of(person);
        System.out.println(optionalPerson);
    }

    @Test
    public void test02() {
        Person person = null;
        Optional<Person> optionalPerson = Optional.ofNullable(person);
        System.out.println(optionalPerson);
    }
}
