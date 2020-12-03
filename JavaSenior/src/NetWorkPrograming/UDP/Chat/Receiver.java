package NetWorkPrograming.UDP.Chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver implements Runnable {
    DatagramSocket socket = null;

    private int myPort;     //本程序的端口号

    public Receiver() {}

    public Receiver(int myPort) {
        this.myPort = myPort;

        try {
            socket = new DatagramSocket(myPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //接收数据包
                byte[] buffer = new byte[1024];     //1kb
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);

                //接收数据包
                socket.receive(packet);

                //获取数据
                String receiveData = new String(packet.getData(), 0, packet.getData().length);

                //输出打印
                System.out.println(receiveData);

                if ("close".equals(receiveData)) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
