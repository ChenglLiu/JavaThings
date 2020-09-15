package Java8.StreamAPI;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author liuclo
 * @Desciption Stream关注对数据的运算，与CPU打交道
 *              集合关注的是数据的存储，与内存打交道
 * @Date 2020/9/15 22:46
 **/

public class StreamAPITest {
    //创建Stream方式一：通过集合
    @Test
    public void test01() {
        List<Person> people = PersonData.getPerson();

        //default Stream<E> stream() : 返回一个顺序流
        Stream<Person> stream = people.stream();

        //default Stream<E> parallelStream() : 返回一个并行流
        Stream<Person> parallelStream = people.parallelStream();
    }

    //创建Stream方式二：通过数组
    @Test
    public void test02() {
        int[] arr = new int[] {1, 2, 3, 4, 5};

        //调用Arrays类的static <T> Stream<T> stream(T[] array) : 返回一个流
        IntStream stream = Arrays.stream(arr);

        Person p1 = new Person("Tom", 18);
        Person p2 = new Person("Jerry", 17);
        Person[] array = new Person[] {p1, p2};
        Stream<Person> personStream = Arrays.stream(array);
    }

    //创建Stream方式三：通过Stream的of()
    @Test
    public void test03() {
        Stream<Integer> integerStream = Stream.of(2, 3, 4, 5, 6);
    }

    //创建Stream方式四：创建无限流
    @Test
    public void test04() {

        //迭代
        //public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
        

        //生成
        //public static <T> Stream<T> generate(Supplier<T> s)
    }
}
