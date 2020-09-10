package Reflection.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理的举例
 */

interface Human {
    String getBelief();

    void eat(String food);
}

//被代理类
class SuperMan implements Human {
    @Override
    public String getBelief() {
        return "I believe i can fly!!!";
    }

    @Override
    public void eat(String food) {
        System.out.println("喜欢吃：" + food);
    }
}

/**
 * 代理类
 * 实现动态代理，需要解决得问题：
 * 1.如何根据加载到内存得被代理类，动态的创建一个代理类及其对象
 * 当通过代理类的对象调用方法时，如何动态调用被代理类的同名方法
 */
class ProxyFactory {
    //调用此方法，返回一个代理类的对象
    public static Object getProxyInstance(Object obj) {     //obj:被代理类的对象
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}

class MyInvocationHandler implements InvocationHandler {
    private Object obj;     //赋值时，需要被代理类的对象
    public void bind(Object obj) {
        this.obj = obj;
    }

    //通过代理类的对象调用方法a时，会自动调用如下的invoke方法
    //将被代理类要执行的方法a的功能声明在method
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method：代理类对象调用的方法，即为被代理类对象要调用的方法
        //obj：被代理类的对象
        Object returnValue = method.invoke(obj, args);
        return returnValue;
    }
}

public class ProxyTest {
    public static void main(String[] args) {
        SuperMan superMan = new SuperMan();
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superMan);

        proxyInstance.getBelief();
        proxyInstance.eat("Hamburger");
    }
}
