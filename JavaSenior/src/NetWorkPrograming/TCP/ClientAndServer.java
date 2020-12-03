package NetWorkPrograming.TCP;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 实现TCP的网络编程
 * @author liucl
 * @date 2020/8/25 10:55
 */

public class ClientAndServer {
    //客户端
    @Test
    public void client() {
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            //1.创建Socket对象，指明服务器端的ip地址和端口号
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inetAddress, 8989);

            //2.获取一个输出流，用于输出数据
            outputStream = socket.getOutputStream();
            //3.写出数据
            outputStream.write("你好，这里是0101".getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭
            if (outputStream != null) {
                try {
                    outputStream.close();
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

    //服务端
    @Test
    public void service() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建服务器端的ServerSocket，指明自己的端口号
            serverSocket = new ServerSocket(8989);
            //2.调用accept()，接受来自客户端的socket
            socket = serverSocket.accept();
            //3.获取输入流
            is = socket.getInputStream();

            //4.读取输入流中的数据
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[7];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.资源关闭
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
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
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
