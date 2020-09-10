package Reflection.Proxy;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 静态代理类
 * 代理类和被代理类在编译期就确定下来了
 */

interface ClothFactory {
    void produceCloth();
}

//代理类
class ProxyClothFactory implements ClothFactory {
    private ClothFactory factory;       //用被代理类对象进行实例化

    public ProxyClothFactory(ClothFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂开始准备...");
        factory.produceCloth();
        System.out.println("代理工厂收尾工作...");
//        produce();
    }

    /*
    public void produce() {
        System.out.println("代理工厂开始准备...");
        factory.produceCloth();
        System.out.println("代理工厂收尾工作...");
    }

     */
}

//被代理类
class NikeCompany implements ClothFactory {
    @Override
    public void produceCloth() {
        System.out.println("NIKE公司生产一批AJ...");
    }
}

//测试
public class StaticProxyTest {
    public static void main(String[] args) {
        //创建被代理类对象
        NikeCompany nikeCompany = new NikeCompany();

        //创建代理类对象
        ClothFactory proxyClothFactory = new ProxyClothFactory(nikeCompany);

        proxyClothFactory.produceCloth();
    }

    @Test
    public void test() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,10,01);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        System.out.println(sdf.format(date));

        System.out.println(date.toString());
    }
}
