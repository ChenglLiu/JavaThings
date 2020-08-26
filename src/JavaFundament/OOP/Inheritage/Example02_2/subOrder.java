package JavaFundament.OOP.Inheritage.Example02_2;

import JavaFundament.OOP.Inheritage.Example02.Order;

public class subOrder extends Order {
    public void method() {
        /*
        orderDefault = -1;      //error
        methodDefault();        //error
         */

        orderProtected = -3;
        methodProtected();
    }
}
