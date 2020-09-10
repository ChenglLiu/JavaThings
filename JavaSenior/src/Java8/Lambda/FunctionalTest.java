package Java8.Lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author liuclo
 * @Desciption 函数式接口
 * @Date  2020/9/10 22:10
 **/

public class FunctionalTest {
    @Test
    //Consumer
    public void test01() {
        purchase(5500.0, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("iPhone12 :" + aDouble);
            }
        });

        System.out.println("-----------Lambda------------");
        purchase(6000, price -> {
            System.out.println("iPhone12 Max : " + price);
        });
    }
    public void purchase(double money, Consumer<Double> con) {
        con.accept(money);
    }

    @Test
    //Predicate
    public void test02() {
        List<String> list = Arrays.asList("上海", "北京", "深圳", "成都", "杭州", "重庆", "南京");
        List<String> ans = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });
        System.out.println(ans);

        System.out.println("*********Lambda*********");
        ans = filterString(list, s -> s.contains("都"));
        System.out.println(ans);
    }
    //根据给定的规则，过滤集合中的字符串，规则由Predicate的方法决定
    public List<String> filterString(List<String> list, Predicate<String> pre) {
        ArrayList<String> filterList = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                filterList.add(s);
            }
        }
        return filterList;
    }
}
