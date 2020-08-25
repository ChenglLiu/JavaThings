package IOStream.IOStreamReaderWriter;

import org.junit.Test;

import java.io.*;

public class FileReaderWriterTest {
    //Reader
    @Test
    public void test01() {
        FileReader fs = null;
        try {
            //实例化File类的对象，指明操作的文件
            File file = new File("Aloha.txt");
//            file.createNewFile();
            //提供具体的流
            fs = new FileReader(file);
            //数据读入
            int data;
            while ((data = fs.read()) != -1) {
                System.out.println("2222");
                System.out.println((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (fs != null) {
                    System.out.println("111");
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Writer
    @Test
    public void test02() {
        FileWriter fw = null;
        try {
            //1.提供File类的对象
            File file = new File("Aloha.txt");

            //2.提供FileWriter对象
            fw = new FileWriter(file, true);

            //3.写出
            fw.write("Aloha World!");
            fw.write("I have a Dream!\n".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    //4.文件流关闭
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读入和写出
    @Test
    public void test03() {
        FileReader fr = null;
        FileWriter fw = null;

        try {
            //1.创建文件对象
            File file1 = new File("Aloha.txt");
            File file2 = new File("Hello.txt");
            //2.创建流对象
            fr = new FileReader(file1);
            fw = new FileWriter(file2);
            //3.数据读入和写出操作
            char[] chb = new char[5];
            int len;
            while ((len = fr.read(chb)) != -1) {
                //每次写出len个字符
                fw.write(chb, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流
            try {
                fw.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //指定文件的复制
    public void copyFile(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            byte[] bf = new byte[1024];
            int len;
            while ((len = fis.read(bf)) != -1) {
                fos.write(bf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test04() {
        long start = System.currentTimeMillis();

        String srcPath = "C:\\Users\\LIuChenglong\\Desktop\\我在浦发开心的一天.mp4";
        String destPath = "C:\\Users\\LIuChenglong\\Desktop\\快乐的一天.mp4";

        copyFile(srcPath, destPath);

        long end = System.currentTimeMillis();

        System.out.println("复制所花费时间: " + (end - start));
    }
}
