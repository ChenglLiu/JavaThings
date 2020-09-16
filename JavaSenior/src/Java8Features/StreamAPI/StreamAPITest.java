package Java8Features.StreamAPI;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
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
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);

        //生成
        //public static <T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    /**
     * @author liuclo
     * @Desciption //TODO
     * @Date 2020/9/16 21:00
     **/
    //中间操作一：筛选与切片
    @Test
    public void test05() {
        List<Person> people = PersonData.getPerson();
        Stream<Person> stream = people.stream();

        //filter(Predicate p)---接收Lambda，从流中排除某些元素
        stream.filter(e -> e.getAge() > 22).forEach(System.out::println);

        System.out.println("----------截断流-------------");
        //limit(n)---截断流，使元素不超过给定数量
        stream = people.stream();
        stream.limit(3).forEach(System.out::println);

        System.out.println("------------跳过----------------");
        //skip(n)---跳过元素，返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回一个空流
        people.stream().skip(3).forEach(System.out::println);

        System.out.println("-------------筛选------------");
        //distinct()---筛选，通过流所生成元素的hashCode()和equals()去除重复元素
        people.stream().distinct().forEach(System.out::println);
    }

    //中间操作二：映射
    @Test
    public void test06() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        //map(Function f)
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

        List<Person> people = PersonData.getPerson();
        Stream<Integer> ageStream = people.stream().map(Person::getAge);
        ageStream.filter(age -> age > 18).forEach(System.out::println);

        Stream<Stream<Character>> streamStream = list.stream().map(StreamAPITest::stringToStream);
        streamStream.forEach(s -> {
            s.forEach(System.out::println);
        });

        //flatMap(Function f)：类似于list.addAll(listA)，将listA中的元素分别加入list，而不是作为整体
        Stream<Character> characterStream = list.stream().flatMap(StreamAPITest::stringToStream);
        characterStream.forEach(System.out::println);
    }

    public static Stream<Character> stringToStream(String str) {
        ArrayList<Character> list = new ArrayList<>();
        for(Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    //中间操作三：排序
    @Test
    public void test07() {
        //sorted():自然排序
        List<Integer> list = Arrays.asList(12, 23, 31, -18, 76, 0, -9);
        list.stream().sorted().forEach(System.out::println);

        //sorted(Comparator com):定制排序
        List<Person> personList = PersonData.getPerson();
        personList.stream().sorted((c1, c2) -> {
            if (c1.getAge() != c2.getAge()) {
                return -Integer.compare(c1.getAge(), c2.getAge());
            } else {
                return c1.getName().compareTo(c2.getName());
            }
        }).forEach(System.out::println);
    }

    /**
     * @author liuclo
     * @Desciption //TODO
     * @Date 2020/9/16 21:30
     **/
    /************终止操作一：匹配与查找***************/
    @Test
    public void test08() {
        List<Person> people = PersonData.getPerson();

        //1. allMatch(Predicate p)---检查是否匹配所有元素
        boolean b = people.stream().allMatch(e -> e.getAge() > 18);
        System.out.println(b);

        //2. anyMatch(Predicate p)---检查是否至少匹配一个元素

        //3. nonMatch(Predicate p)---检查是否没有匹配的元素
        System.out.println(people.stream().noneMatch(e -> e.getName().startsWith("l")));

        //4. Optional findFirst()---返回第一个元素
        Optional<Person> first = people.stream().findFirst();
        System.out.println(first);

        //5. findAny()---返回当前流中的任意元素

        //6. long count()---返回流中元素的个数

        //7. max(Comparator c)---返回流中最大值/min(Comparator c)---返回流中最小值
        Stream<Integer> ageStream = people.stream().map(e -> e.getAge());
        Optional<Integer> maxAge = ageStream.max(Integer::compare);
        System.out.println(maxAge);         //Optional[25]

        Optional<Person> min = people.stream().min(Comparator.comparingInt(Person::getAge));
        System.out.println(min);            //Optional[Person{name='wyc', age=22}]

        //8. forEach(Consumer c)---内部迭代
    }

    /*************终止操作二：规约****************/
    @Test
    public void test09() {
        //T reduce(T identity, BinaryOperator)---将流中元素结合起来，得到一个值，返回T
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);        //55

        //Optional<T> reduce(BinaryOperator)
        List<Person> people = PersonData.getPerson();
        Stream<Integer> ageStream = people.stream().map(Person::getAge);
        Optional<Integer> sumReduce = ageStream.reduce((a1, a2) -> a1 + a2);
        System.out.println(sumReduce);      //Optional[117]
    }

    /**************终止操作三：收集******************/
    @Test
    public void test10() {
        //collect(Collector c)---将流转为其他形式，接收一个Collector接口的实现
        List<Person> people = PersonData.getPerson();
        List<Person> list = people.stream().filter(p -> p.getAge() >= 23).collect(Collectors.toList());
        list.forEach(System.out::println);
    }
}
