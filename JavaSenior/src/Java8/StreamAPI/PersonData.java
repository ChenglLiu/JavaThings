package Java8.StreamAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuclo
 * @Desciption //TODO
 * @Date 2020/9/15 22:52
 **/

public class PersonData {
    public static List<Person> getPerson() {
        List<Person> list = new ArrayList<>();

        list.add(new Person("hzx", 23));
        list.add(new Person("lcl", 23));
        list.add(new Person("wyc", 22));
        list.add(new Person("yls", 24));
        list.add(new Person("lls", 25));

        return list;
    }
}

class Person {
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
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
