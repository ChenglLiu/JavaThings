package Generic.GenericTest;

import org.junit.Test;

import java.util.*;

public class GenericTest {
    //集合中未使用泛型
    @Test
    public void test01() {
        ArrayList list = new ArrayList();
        list.add(89);
        list.add(94);
        list.add("Tom");

        for (Object obj : list) {
            int score = (Integer)obj;
            //ClassCastException
            System.out.println(score);
        }
    }


    //集合中使用泛型
    @Test
    public void test02() {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(77);
        list.add(87);
        list.add(93);

        //编译时会执行类型检查
        //list.add("Tom");          //error

        //方式一：
        for (Integer integer : list) {
            //避免了强转
            int score = integer;
            System.out.println(score);
        }

        //方式二：
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int score = iterator.next();
            System.out.println(score);
        }
    }

    //以HashMap为例
    @Test
    public void test03() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Tom", 77);
        map.put("Jerry", 86);
        map.put("Mike", 92);

        //泛型的嵌套
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }

    @Test
    public void test04() {
        /*
        Order order = new Order();
        order.setOrderT(123);
        order.setOrderT("ABC");
         */

        Order<String> order = new Order<>("Plus", 23, "SNU");
        order.setOrderT("SCU");
        System.out.println(order.getOrderT());

        subOrder subOrder = new subOrder();
        subOrder.setOrderT(999);
        System.out.println(subOrder.getOrderT());
    }

    //测试泛型方法
    @Test
    public void test05() {
        Order<String> order = new Order<>();
        Integer[] arr = new Integer[]{1, 2, 3, 4};
        //泛型方法调用时指明泛型参数的类型
        List<Integer> integers = order.copyFromArrayToList(arr);
        System.out.println(integers);
    }
}
