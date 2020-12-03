package NetWorkPrograming.UDP.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Sender implements Runnable {
    DatagramSocket socket = null;
    BufferedReader reader = null;

    private int receivePort;        //接收方端口号
    private String receiveIp;       //接收方ip地址
    private int myPort;             //本程序的端口号

    public Sender() {}

    public Sender(int receivePort, String receiveIp, int myPort) {
        this.receivePort = receivePort;
        this.receiveIp = receiveIp;
        this.myPort = myPort;

        //初始化端口 和 输入流
        try {
            socket = new DatagramSocket(myPort);
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String data = null;
            try {
                //读数据
                data = reader.readLine();
                byte[] buffer = data.getBytes();

                //建数据包
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length,
                                InetAddress.getByName(receiveIp), receivePort);

                //发送数据
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if ("close".equals(data)) {
                break;
            }
        }
        socket.close();
    }
}
