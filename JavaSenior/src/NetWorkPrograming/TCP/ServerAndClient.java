package NetWorkPrograming.TCP;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAndClient {
    //客户端
    @Test
    public void client() {
        OutputStream os = null;
        Socket socket = null;
        FileInputStream fis = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Socket对象，指明服务器端的ip地址和端口号
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inetAddress, 8989);

            //2.获取一个输出流，用于输出数据
            os = socket.getOutputStream();

            //3.获取文件流
            fis = new FileInputStream(new File("Aloha.txt"));

            //4.写出数据
            byte[] buffer1 = new byte[7];
            int len1;
            while ((len1 = fis.read(buffer1)) != -1) {		//read:
                os.write(buffer1, 0, len1);
            }
//        outputStream.write("你好，这里是0101".getBytes());

            //5.关闭数据的输出
            socket.shutdownOutput();

            //6.接受来自服务端的回应，显示到控制台上
            is = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buffer2 = new byte[9];
            int len2;
            while ((len2 = is.read(buffer2)) != -1) {
                baos.write(buffer2, 0, len2);
            }
            System.out.println(baos.toString());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭
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

    //服务端
    @Test
    public void service() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        FileOutputStream fos = null;
        try {
            //1.创建服务器端的ServerSocket，指明自己的端口号
            serverSocket = new ServerSocket(8989);

            //2.调用accept()，接受来自客户端的socket
            socket = serverSocket.accept();

            //3.获取输入流
            is = socket.getInputStream();

            //4.获取文件流
            fos = new FileOutputStream(new File("dest.txt"));

            //5.读取数据
            byte[] buffer = new byte[7];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            System.out.println("传输完成...");

            //6.服务器端给与客户端反馈
            os = socket.getOutputStream();
            os.write("已经收到文件！！！".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //7.资源关闭
            if (fos != null) {
                try {
                    fos.close();
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
