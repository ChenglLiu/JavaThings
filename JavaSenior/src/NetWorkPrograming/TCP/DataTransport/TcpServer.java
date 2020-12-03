package NetWorkPrograming.TCP.DataTransport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuclo
 * @Desciption //服务端
 * @Date  2020/12/1
 **/
public class TcpServer {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        Socket socket = null;
        ServerSocket serverSocket = null;
        try {
            //1.创建服务器端，指明自己的端口号
            serverSocket = new ServerSocket(8888);

            //2.接收来自客户端的socket
            socket = serverSocket.accept();

            //3.获取输入流
            is = socket.getInputStream();

            //4.读取流中的数据
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len=is.read(buffer)) != -1) {
                baos.write(buffer, 0 ,len);
            }
            System.out.println(baos.toString());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
