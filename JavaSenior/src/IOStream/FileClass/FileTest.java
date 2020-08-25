package IOStream.FileClass;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Description File类
 * @author licl
 * @date 2020/8/16 16:19
 */

public class FileTest {
    @Test
    public void test01() {
        //现在理解为内存层面的一个对象
        //ctor-1
        File file1 = new File("hello.txt");             //相对路径，相对于当前module
        File file2 = new File("D:\\Code\\git_Code\\HH_STUDY\\" +
                "JavaThings\\JavaSenior\\src\\IOStream\\FileClass\\Aloha.txt");
        System.out.println(file1);
        System.out.println(file2);
        System.out.println(file1.canRead());

        //ctor-2
        File file3 = new File("D:\\Code\\git_Code", "HH_STUDY\\JavaThings");
        System.out.println(file3);

        //ctor-3
        File file4 = new File(file3, "Aloha.txt");
        System.out.println(file4);
    }

    @Test
    public void test02() {
        File file = new File("D:\\Code\\git_Code\\HH_STUDY\\JavaThings");
        String[] list = file.list();
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("-----------");

        File[] listFiles = file.listFiles();
        for (File file1 : listFiles) {
            System.out.println(file1);
        }
    }

    @Test
    public void test03() {
        File file = new File("Aloha.txt");
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getPath());
        System.out.println(file.getName());
        System.out.println(file.getParent());
        System.out.println(file.length());
        System.out.println(file.lastModified());
        System.out.println(new Date(file.lastModified()));
    }

    //public boolean renameTo(File dest)：把文件重命名为指定的文件路径
    @Test
    public void test04() {
        File file1 = new File("Aloha.txt");
        File file2 = new File("D:\\Code\\Hello.txt");

        //true: file1 must be exist and file is not exist
        //else: false
        boolean renameTo = file1.renameTo(file2);
        System.out.println(renameTo);

        //再将文件转换回来
        //boolean renameTo = file2.renameTo(file1);

        System.out.println(file2.delete());
    }

    @Test
    public void test05() {
        File file = new File("Aloha.txt");

        System.out.println(file.isDirectory());
        System.out.println(file.isFile());
        System.out.println(file.exists());
        System.out.println(file.canWrite());
        System.out.println(file.canRead());
        System.out.println(file.isHidden());

        System.out.println("-------");

        File file1 = new File("D:\\Code\\git_Code");
        System.out.println(file1.isDirectory());
        System.out.println(file1.isFile());
        System.out.println(file1.exists());
        System.out.println(file1.canWrite());
        System.out.println(file1.canRead());
        System.out.println(file1.isHidden());
    }

    //文件创建
    @Test
    public void test06() {

        File file = new File("hello.txt");
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("创建成功...");
        } else {
            file.delete();
            System.out.println("删除成功...");
        }
    }

    //文件目录的创建
    @Test
    public void test07() {
        File file1 = new File("D:\\Code\\git_Code\\IO\\IO01");

        boolean mkdir = file1.mkdir();
        if (mkdir) {
            System.out.println("IO01创建成功...");
        }

        File file2 = new File("D:\\Code\\git_Code\\IO\\IO02");

        boolean mkdirs = file2.mkdirs();
        if (mkdirs) {
            System.out.println("IO02创建成功...");
        }


        File file = new File("D:\\Code\\git_Code\\IO\\IO02");
        System.out.println(file.delete());
    }

    @Test
    public void test08() {
        File file = new File("D:\\Code\\git_Code\\IO\\Hello.txt");

        File destFile = new File(file.getParent(), "Aloha.txt");
        boolean newFile = false;
        try {
            newFile = destFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newFile) {
            System.out.println("创建成功...");
        }
    }
}
