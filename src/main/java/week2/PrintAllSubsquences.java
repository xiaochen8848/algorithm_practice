package week2;

import java.util.HashSet;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/8 - 04 - 08 - 18:51
 * @Description: week2
 * @version: 1.0
 */
// 打印一个字符串的所有子序列
public class PrintAllSubsquences {

    public static void process(char[] str, int index, String path) {
        if (index == str.length) {
            System.out.println(path);
            return;
        }

        // 当前index位置的决策有 要/不要
        process(str, index + 1, path);
        process(str, index + 1, path + str[index]);
    }

    // 打印一个字符串的所有子序列，并且要去重
    public static void process1(char[] str, int index, String path, HashSet<String> set) {
        if (index == str.length) {
            if (set.add(path)) {
                System.out.println(path);
            }
            return;
        }

        // 当前index位置的决策有  要/不要
        process1(str, index + 1, path, set);
        process1(str, index + 1, path + str[index], set);
    }

    public static void main(String[] args) {
        String s = "acc";
        char[] str = s.toCharArray();
        process(str, 0, "");
        System.out.println("=======================");
        process1(str, 0, "", new HashSet<>());
    }
}
