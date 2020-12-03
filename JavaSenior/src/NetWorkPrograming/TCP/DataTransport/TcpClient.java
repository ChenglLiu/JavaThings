package NetWorkPrograming.TCP.DataTransport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author liuclo
 * @Desciption 客户端
 * @Date  2020/12/1
 **/
public class TcpClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        OutputStream os = null;
        Socket socket = null;
        try {
            //1.获取ip，端口号
            InetAddress serverIP = InetAddress.getByName("127.0.0.1");
            int port = 8888;

            //2.创建socket连接
            socket = new Socket(serverIP, port);
            os = socket.getOutputStream();

            //3.发送消息
            os.write("你好".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
