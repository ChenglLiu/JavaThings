package IOStream.TransferStream;

import org.junit.Test;

import java.io.*;

/**
 * @Description 转换流
 * @author liucl
 * @date 2020/8/19 20:57
 */

public class InputStreamReaderTest {
    //InputStreamReader:字节输入流--->字符输入流
    @Test
    public void test01() {
        InputStreamReader isr = null;

        try {
            FileInputStream fis = new FileInputStream("Aloha.txt");
//        InputStreamReader isr = new InputStreamReader(fis);     //使用系统默认的字符集
            isr = new InputStreamReader(fis, "UTF-8");

            char[] chbf = new char[20];
            int len;
            while ((len = isr.read(chbf)) != -1) {
                String str = new String(chbf, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //InputStreamReader & OutputStreamWriter ：
    // 字节输入流--->字符输入流，字符输出流--->字节输出流
    @Test
    public void test02() {
        File file1 = new File("Aloha.txt");
        File file2 = new File("Aloha_gbk.txt");

        InputStreamReader isr = null;
        OutputStreamWriter osw = null;

        try {
            FileInputStream fis = new FileInputStream(file1);
            FileOutputStream fos = new FileOutputStream(file2);

            isr = new InputStreamReader(fis, "utf-8");
            osw = new OutputStreamWriter(fos, "gbk");

            char[] chbf = new char[10];
            int len;
            while ((len = isr.read(chbf)) != -1) {
                String str = new String(chbf, 0, len);
                osw.write(str, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
