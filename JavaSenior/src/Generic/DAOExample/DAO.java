package Generic.DAOExample;

/**
 * @Description
 * @author liucl
 * @date 2020/8/15 15:00
 */

import java.util.*;

public class DAO<T> {       //表的共性操作
    private Map<String, T> map = new HashMap<>();

    //保存T类型的对象到Map成员中
    public void save(String id, T entity) {
        map.put(id, entity);
    }

    //从map获取指定id的对象
    public T get(String id) {
        return map.get(id);
    }

    //替换map中key为id的内容，改为entity对象
    public void update(String id, T entity) {
        if (map.containsKey(id)) {
            map.put(id, entity);
        }
    }

    //返回map中存放的所有T对象
    public List<T> list() {
        //error
        //map.values()返回的是一个Collection的实现类的对象
        //Collection<T> values = map.values();
        //return (List<T>)values;

        //ok
        List<T> list = new ArrayList<>();
        Collection<T> values = map.values();
        for (T t : values) {
            list.add(t);
        }
        return list;
    }

    //删除指定的id对象
    public void delete(String id) {
        map.remove(id);
    }

    //-------------------------
    //查询
    public T getIndex(int index) {
        return null;
    }

    public List<T> getForList(int index) {
        return null;
    }

    //泛型方法
    //1. 获取表中的记录总数 return Integer
    //2. 获取某日期 return Date
    //3. 获取名字 return String
    public <E> E getValue() {
        return null;
    }
}
