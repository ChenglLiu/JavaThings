package IOStream.FileClass;

import java.io.File;

/**
 * @Description 遍历指定目录的所有文件名称，包括子目录的文件
 * @author liucl
 * @date 2020/8/16 20:46
 */

public class ListFiles {
    public static void main(String[] args) {
        //递归 文件目录

        //1. 创建目录对象
        File dir = new File("C:\\Users\\LIuChenglong\\Pictures");

        //2. 打印目录的子文件
        int sumMemory = 0;
        System.out.println(printSubFile(dir, sumMemory));

        System.out.println("***********************");

        //1) 创建目录对象
        File file = new File("D:\\Code\\git_Code\\IO");

        //2) 删除子文件
        ListFiles listFiles = new ListFiles();
        listFiles.deleteDirectory(file);
    }

    //打印所有文件目录
    private static int printSubFile(File dir, int sumMemory) {
        //打印目录的子文件
        File[] subFiles = dir.listFiles();

        for (File file : subFiles) {
            if (file.isDirectory()) {
                printSubFile(file, sumMemory);
            } else {
                sumMemory += file.length();
                System.out.println(file.getAbsolutePath());
            }
        }
        return sumMemory;
    }

    private void deleteDirectory(File file) {
        //如果是文件，则直接删除
        //如果是目录，则先删除下一级文件，再删除自己
        if (file.isDirectory()) {
            File[] allFiles = file.listFiles();
            //删除下一级目录文件
            for (File f : allFiles) {
                System.out.print(f);
                System.out.println("\t删除成功...");
                deleteDirectory(f);
            }
        }
        file.delete();
    }
}
