package IOStream.FileClass;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @Description 判断指定目录下是否有.jpg文件，如果有，则输出文件名
 * @author liucl
 * @date 2020/8/16 20:40
 */

public class FindJPGFile {
    @Test
    public void test01() {
        File srcFile = new File("D:\\Code");

        String[] fileNames = srcFile.list();
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
    }

    @Test
    public void test02() {
        File srcFile = new File("D:\\Code");

        File[] subFiles = srcFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });

        for (File file : subFiles) {
            System.out.println(file);
        }
    }
}
