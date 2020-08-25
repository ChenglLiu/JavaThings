package Generic.GenericTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descripton 自定义泛型类
 * @author liucl
 * @date 2020/8/14 22:54
 */

public class Order<T> {
    private String name;
    private int id;

    //类的内部结构可以使用类的泛型
    T orderT;

    public Order() {}
    public Order(String name, int id, T orderT) {
        this.name = name;
        this.id = id;
        this.orderT = orderT;
    }

    public T getOrderT() {
        return orderT;
    }

    public void setOrderT(T orderT) {
        this.orderT = orderT;
    }

    public <E> List<E> copyFromArrayToList(E[] arr) {
        ArrayList<E> list = new ArrayList<>();
        for (E e : arr) {
            list.add(e);
        }
        return list;
    }
}
