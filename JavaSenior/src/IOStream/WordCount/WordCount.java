package IOStream.WordCount;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description 统计文本上字符出现的个数，并写入一个文件
 * @author liucl
 * @date 2020/8/18
 */

public class WordCount {
    public static void main(String[] args) {
        FileReader fr = null;
        BufferedWriter bw = null;
        try {
            File file = new File("test.txt");

            //1.创建Map集合
            Map<Character, Integer> map = new HashMap<>();

            //2.遍历test.txt文件中的字符
            fr = new FileReader(file);
            int data = 0;
            while ((data = fr.read()) != -1) {
                //3.还原成字符
                char ch = (char)data;
                if (map.get(ch) == null) {
                    map.put(ch, 1);
                } else {
                    map.put(ch, map.get(ch) + 1);
                }
            }

            //4.将map中的数据存入count.txt文件中
            FileWriter fw = new FileWriter("count.txt");
            bw = new BufferedWriter(fw);

            //5.遍历map，再写入数据
            Set<Map.Entry<Character, Integer>> entrySet = map.entrySet();
            for (Map.Entry<Character, Integer> entry : entrySet) {
                switch (entry.getKey()) {
                    case ' ':
                        bw.write("空格=" + entry.getValue());
                        break;
                    case '\t':
                        bw.write("tab键=" + entry.getValue());
                        break;
                    case '\r':
                        bw.write("回车=" + entry.getValue());
                        break;
                    case '\n':
                        bw.write("换行" + entry.getValue());
                        break;
                    default:
                        bw.write(entry.getKey() + "=" + entry.getValue());
                        break;
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭流
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
