package IOStream.IOStreamReaderWriter;

import org.junit.Test;

import java.io.*;

/**
 * @Description BufferedInputStream\BufferedOutputStream
 * @author liucl
 * @date 2020/8/17 21:30
 */

public class BufferedInOutStream {
    //实现非文本的复制
    @Test
    public void test01() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //1.拿到文件对象
            File srcFile = new File("C:\\Users\\LIuChenglong\\Desktop\\我在浦发开心的一天.mp4");
            File destFile = new File("C:\\Users\\LIuChenglong\\Desktop\\happy.mp4");
            //2.造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            //3.造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            //4.复制
            byte[] buffer = new byte[10];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //实现文件的复制
    public void copyFile(String srcPath, String destPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //1.造文件对象
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);
            //2.造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            //3.造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            //4.复制
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test02() {
        long start = System.currentTimeMillis();

        String srcPath = "C:\\Users\\LIuChenglong\\Desktop\\我在浦发开心的一天.mp4";
        String destPath = "C:\\Users\\LIuChenglong\\Desktop\\haha.mp4";

        copyFile(srcPath, destPath);

        long end = System.currentTimeMillis();

        System.out.println("花费时间：" + (end - start));
    }
}
