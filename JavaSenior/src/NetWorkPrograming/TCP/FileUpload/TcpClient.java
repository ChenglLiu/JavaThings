package NetWorkPrograming.TCP.FileUpload;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author liuclo
 * @Desciption //客户端
 * @Date  2020/12/2
 **/

public class TcpClient {
    public static void main(String[] args) {

        ByteArrayOutputStream baos = null;
        InputStream is = null;
        OutputStream os = null;
        FileInputStream fis = null;
        Socket socket = null;
        try {
            //创建连接
            InetAddress serverIp = InetAddress.getLocalHost();
            int port = 9999;
            socket = new Socket(serverIp, port);

            //发送文件
            File file = new File("D:\\Code\\workspace_IDEA\\JavaThings\\JavaSenior\\src\\NetWorkPrograming\\TCP\\FileUpload\\src.txt");
            fis = new FileInputStream(file);

            os = socket.getOutputStream();

            os.write("开始传输...\n".getBytes());
            byte[] buffer = new byte[7];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.write("\n传输结束...\n".getBytes());

            //接收服务端返回的消息，打印至控制台
            is = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            System.out.println(baos.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
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
