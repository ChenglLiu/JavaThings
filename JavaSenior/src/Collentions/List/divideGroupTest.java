package Collentions.List;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class divideGroupTest {
    public static void main(String[] args) {
        List<GroupInfo> groupInfos = new ArrayList<>();
        groupInfos.add(new GroupInfo("AA", 23, 1, false));
        groupInfos.add(new GroupInfo("BB", 19, 1, false));
        groupInfos.add(new GroupInfo("CC", 18, 2, true));
        groupInfos.add(new GroupInfo("DD", 18, 2, true));
        groupInfos.add(new GroupInfo("EE", 18, 2, false));
        groupInfos.add(new GroupInfo("FF", 18, 1, true));

        Map<Integer, Map<Boolean, List<GroupInfo>>> map =
                groupInfos.stream().collect(Collectors.groupingBy(GroupInfo::getId,
                        Collectors.groupingBy(GroupInfo::getStudent)));

        for (Map<Boolean, List<GroupInfo>> value : map.values()) {
            for (List<GroupInfo> infos : value.values()) {
                System.out.println(infos.toString());
            }
        }


        System.out.println("测试开始");
        int[] arr = new int[]{1, 2, 3};
        try {
            for (int i=0; i<=3; i++) {
                System.out.println("arr[" + i + "]: " + arr[i]);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("测试完成");
        }

        System.out.println("----------------------");
        //只需要id和student两个字段，取list第一条即可
        for (Map<Boolean, List<GroupInfo>> value : map.values()) {
            for (List<GroupInfo> infos : value.values()) {
                System.out.println(infos.get(0));
            }
        }

        System.out.println("------------------------");
        divideGroupTest test = new divideGroupTest();
        test.method(groupInfos, data -> {
            data.forEach(d -> System.out.println(d.toString()));
        });
    }

    public void method(List<GroupInfo> list, Consumer<List<GroupInfo>> consumer) {
        consumer.accept(list);
    }
}

