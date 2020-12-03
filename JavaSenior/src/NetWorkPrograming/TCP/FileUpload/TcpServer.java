package NetWorkPrograming.TCP.FileUpload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuclo
 * @Desciption //服务端
 * @Date  2020/12/2
 **/
public class TcpServer {
    public static void main(String[] args) {

        OutputStream os = null;
        FileOutputStream fos = null;
        InputStream is = null;
        Socket socket = null;
        ServerSocket serverSocket = null;
        try {
            //建立连接
            serverSocket = new ServerSocket(9999);

            socket = serverSocket.accept();

            //接收输入
            is = socket.getInputStream();

            File file = new File("D:\\Code\\workspace_IDEA\\JavaThings\\JavaSenior\\src\\NetWorkPrograming\\TCP\\FileUpload\\dest.txt");

            fos = new FileOutputStream(file);

            byte[] buffer = new byte[7];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0 , len);
            }

            //反馈给客户端
            os = socket.getOutputStream();
            os.write("接收完毕...".getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
